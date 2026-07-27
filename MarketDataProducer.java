package com.poojitha.trading.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poojitha.trading.model.Trade;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MarketDataProducer {

    private static final String TOPIC = "market-data-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public MarketDataProducer(KafkaTemplate<String, String> kafkaTemplate,
                              ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishTrade(Trade trade) {

        try {

            String message = objectMapper.writeValueAsString(trade);

            kafkaTemplate.send(TOPIC, trade.getSymbol(), message);

            System.out.println("--------------------------------------");
            System.out.println("Trade Published");
            System.out.println("Topic  : " + TOPIC);
            System.out.println("Symbol : " + trade.getSymbol());
            System.out.println("Action : " + trade.getAction());
            System.out.println("Price  : " + trade.getMarketPrice());
            System.out.println("Qty    : " + trade.getQuantity());
            System.out.println("--------------------------------------");

        } catch (JsonProcessingException exception) {

            System.err.println("Unable to serialize trade.");

            exception.printStackTrace();
        }
    }

    public void publishMarketPrice(String symbol,
                                   double currentPrice) {

        String payload = symbol + "," + currentPrice;

        kafkaTemplate.send(TOPIC, symbol, payload);

        System.out.println("Market Price Published : " + payload);
    }

    public void publishSystemNotification(String message) {

        kafkaTemplate.send("system-events", message);

        System.out.println("System Event : " + message);
    }

}
