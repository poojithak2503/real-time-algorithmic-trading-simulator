package com.poojitha.trading.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class MarketDataConsumer {

    private final Map<String, Double> latestPrices = new HashMap<>();

    @KafkaListener(
            topics = "market-data-topic",
            groupId = "trading-group"
    )
    public void consume(String message) {

        System.out.println();
        System.out.println("========== MARKET EVENT ==========");
        System.out.println("Time    : " + LocalDateTime.now());
        System.out.println("Payload : " + message);
        System.out.println("==================================");
    }

    @KafkaListener(
            topics = "market-price-topic",
            groupId = "price-group"
    )
    public void consumePrice(String message) {

        try {

            String[] values = message.split(",");

            String symbol = values[0];

            double price = Double.parseDouble(values[1]);

            latestPrices.put(symbol, price);

            System.out.println(symbol + " Latest Price : " + price);

        } catch (Exception exception) {

            System.out.println("Invalid market price message.");

        }
    }

    public double getLatestPrice(String symbol) {

        return latestPrices.getOrDefault(symbol, 0.0);

    }

    public Map<String, Double> getMarketSnapshot() {

        return new HashMap<>(latestPrices);

    }

    public boolean hasSymbol(String symbol) {

        return latestPrices.containsKey(symbol);

    }

    public void removeSymbol(String symbol) {

        latestPrices.remove(symbol);

    }

    public int totalTrackedStocks() {

        return latestPrices.size();

    }

}
