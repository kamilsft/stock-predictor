package com.kamil.stockpredictor.repository;

import com.kamil.stockpredictor.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Custom method to find all transactions for a user
    List<Transaction> findByUserId(Long userId);
}
