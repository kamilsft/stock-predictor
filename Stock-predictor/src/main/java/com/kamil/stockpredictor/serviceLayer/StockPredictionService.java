package com.kamil.stockpredictor.serviceLayer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamil.stockpredictor.Prediction;
import com.kamil.stockpredictor.Stock;
import com.kamil.stockpredictor.repository.PredictionRepository;
import com.kamil.stockpredictor.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import weka.core.*;
import weka.classifiers.functions.LinearRegression;

import java.util.ArrayList;


@Service
public class StockPredictionService {

    @Autowired
    private APIservice apIservice;

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private StockRepository stockRepository;

    // fetch closing prices using alpha vantage
    public List<Double> getClosingPrices(String symbol) throws Exception{
        String jsonData = apIservice.getStockData(symbol);

        // parse json using jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);

        JsonNode timeSeries = rootNode.get("Time Series (Daily)");
        List<Double> closingPrices = new ArrayList<>();

        if(timeSeries != null){
            // looping through the data and collecting the closing prices
            for(Iterator<String> it = timeSeries.fieldNames(); it.hasNext(); ){
                String date = it.next();
                JsonNode dailyData = timeSeries.get(date);
                double closePrice = dailyData.get("4. close").asDouble();
                closingPrices.add(closePrice);
            }
        }
        return closingPrices; //  Returning the list of closing prices for the stock
    }

    // Training the simple model using Weka (LINEAR REGRESSION)
    public LinearRegression trainModel(List<Double> closingPrices) throws Exception{
        FastVector attributes = new FastVector(1);
        attributes.addElement(new Attribute("close"));

        Instances dataset = new Instances("StockPriceData", attributes, closingPrices.size());
        dataset.setClassIndex(0);

        // adding closing prices at instances
        for(Double price : closingPrices){
            Instance instance = new DenseInstance(1);
            instance.setValue((Attribute) attributes.elementAt(0), price);
            dataset.add(instance);
        }

        // training a linear regression model
        LinearRegression model = new LinearRegression();
        model.buildClassifier(dataset);

        return model;
    }

    // predicting the next price using the trained model
    public double predictNextPrice(LinearRegression model, double[] input) throws Exception {
        Instance instance = new DenseInstance(1);
        instance.setValue(0, input[0]); // using the last known closing price as input

        return model.classifyInstance(instance); // predicting the next closing price
    }

    public double predictStockPrice(String symbol) throws Exception {
        // fetching closing prices
        List<Double> closingPrices = getClosingPrices(symbol);
        // training using closing prices
        LinearRegression model = trainModel(closingPrices);

        //  predicting the next price based on the latest closing price
        double[] latestPrice = { closingPrices.get(closingPrices.size() - 1) };

        return predictNextPrice(model, latestPrice);
    }

    // saving the prediction to database
    public void savePrediction(String symbol, double predictedPrice){
        // fetching the Stock entity using the symbol
        Stock stock = stockRepository.findBySymbol(symbol);

        if(stock == null){
            // if stock is not found create a new stock entity
            stock = new Stock();
            stock.setSymbol(symbol);
            stock.setCompanyName("Unknown Company");
            stockRepository.save(stock);
        }

        Prediction prediction = new Prediction();
        prediction.setPredictedPrice(predictedPrice);
        prediction.setStock(stock);
        prediction.setPredictionDate(new Date());

        predictionRepository.save(prediction);
    }
}





















