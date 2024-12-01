package com.kamil.stockpredictor.serviceLayer;

import com.kamil.stockpredictor.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kamil.stockpredictor.*;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Stock getStockBySymbol(String symbol){
        return stockRepository.findBySymbol(symbol);
    }

    public Stock saveStock(Stock stock){
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id){
        stockRepository.deleteById(id);
    }
}
