package com.kamil.stockpredictor.serviceLayer;


import com.kamil.stockpredictor.Prediction;
import com.kamil.stockpredictor.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    public List<Prediction> getAllPredictions(){
        return predictionRepository.findAll();
    }

    public Prediction savePrediction(Prediction prediction){
        return predictionRepository.save(prediction);
    }

    public void deletePrediction(Long id){
        predictionRepository.deleteById(id);
    }

    public Prediction getPredictionByStock(String symbol) {
        return predictionRepository.findByStockSymbol(symbol);
    }
}
