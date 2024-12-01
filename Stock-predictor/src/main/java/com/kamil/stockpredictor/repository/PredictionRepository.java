package com.kamil.stockpredictor.repository;


import com.kamil.stockpredictor.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    Prediction findByStockSymbol(String symbol);

}
