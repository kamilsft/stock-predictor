package com.kamil.stockpredictor.repository;


/**
 Spring Data JPA, a repository which used to interact with the database, providing methods to
 perform common database operations.
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kamil.stockpredictor.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findBySymbol(String symbol); // Find stock by its symbol
}
