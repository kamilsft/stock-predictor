package com.kamil.stockpredictor.controller;

import com.kamil.stockpredictor.serviceLayer.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.kamil.stockpredictor.Prediction;
import java.util.List;
import org.springframework.ui.Model;


@RestController
@RequestMapping("/api/predictions")
public class PredictionContoller {

    @Autowired
    private PredictionService predictionService;

    @GetMapping
    public List<Prediction> getAllPredictions(){
        return predictionService.getAllPredictions();
    }

    @PostMapping
    public Prediction addPrediction(@RequestBody Prediction prediction){
        return predictionService.savePrediction(prediction);
    }

    @DeleteMapping("/{id}")
    public void deletePrediction(@PathVariable Long id){
        predictionService.deletePrediction(id);
    }

    @GetMapping("/search/prediction")
    public String getStockPrediction(@RequestParam String symbol, Model model){
        Prediction prediction = predictionService.getPredictionByStock(symbol);

        if(prediction != null){
            model.addAttribute("symbol", symbol);
            model.addAttribute("predictedPrice", prediction.getPredictedPrice());
            model.addAttribute("confidence", prediction.getConfidence());
        } else {
            model.addAttribute("error", "No prediction available for this stock");
        }

        return "search-result";
    }
}
