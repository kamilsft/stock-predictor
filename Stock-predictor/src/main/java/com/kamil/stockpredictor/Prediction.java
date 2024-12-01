package com.kamil.stockpredictor;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double predictedPrice;
    private Double confidence;
    private Date predictionDate;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    public Prediction() {
    }

    public Prediction(Double predictedPrice, Double confidence, Date predictionDate, Stock stock) {
        this.predictedPrice = predictedPrice;
        this.confidence = confidence;
        this.predictionDate = predictionDate;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPredictedPrice() {
        return predictedPrice;
    }

    public void setPredictedPrice(Double predictedPrice) {
        this.predictedPrice = predictedPrice;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Date getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(Date predictionDate) {
        this.predictionDate = predictionDate;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
