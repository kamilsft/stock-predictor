package com.kamil.stockpredictor.serviceLayer;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class APIservice {

    private static final String API_KEY = "RYRTIT7ND7V10NTQ";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    public String getStockData(String symbol){
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Build the URL to call Alpha Vantage's TIME SERIES DAILY endpoint
            // to get the daily historical stock data
            String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("function",  "TIME_SERIES_DAILY")
                    .queryParam("symbol", symbol)
                    .queryParam("apikey", API_KEY)
                    .toUriString();

            // Fetching the response
            String response = restTemplate.getForObject(uri, String.class);
            System.out.println("Alpha Vantage Response: " + response);
            return response;
        } catch (Exception e) {
            System.out.println("Error while fetching stock data: " + e.getMessage());
            return "Error fetching stock data";
        }
    }
}
