package com.kamil.stockpredictor.controller;


import com.kamil.stockpredictor.serviceLayer.APIservice;
import com.kamil.stockpredictor.serviceLayer.StockPredictionService;
import com.kamil.stockpredictor.serviceLayer.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.kamil.stockpredictor.Stock;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockPredictionService stockPredictionService;

    @Autowired
    private APIservice apIservice;

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> getAllStocks(){
        return stockService.getAllStocks();
    }

    @GetMapping("/{symbol}")
    public Stock getStockBySymbol(@PathVariable("symbol") String symbol) {  // Explicit @PathVariable parameter name
        return stockService.getStockBySymbol(symbol);
    }
    @PostMapping
    public Stock addStock(@RequestBody Stock stock){
        return stockService.saveStock(stock);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id){
        stockService.deleteStock(id);
    }

    // Fetching real time stock data from Alpha Vantage
    @GetMapping("/fetch/{symbol}")
    public String fetchStockData(@PathVariable("symbol") String symbol) {  // Explicit @PathVariable parameter name
        return apIservice.getStockData(symbol);  // Fetch real-time data using AlphaVantageService
    }

    // predicting the stock price
    @GetMapping("/predict/{symbol}")
    public double predictStockPrice(@PathVariable("symbol") String symbol) throws Exception {
        double predictedPrice = stockPredictionService.predictStockPrice(symbol); // Predicting the next stock price

        stockPredictionService.savePrediction(symbol, predictedPrice);

        return predictedPrice;
    }
}
