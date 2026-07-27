package com.poojitha.trading.websocket;

import com.poojitha.trading.model.Portfolio;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PortfolioWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public PortfolioWebSocketService(
            SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;

    }

    public void publishPortfolio(Portfolio portfolio) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("trader", portfolio.getTrader());
        payload.put("holdings", portfolio.getHoldings());
        payload.put("investedAmount", portfolio.getInvestedAmount());
        payload.put("currentValue", portfolio.getCurrentValue());
        payload.put("profitLoss", portfolio.calculateProfitLoss());
        payload.put("generatedTime", LocalDateTime.now());

        messagingTemplate.convertAndSend(
                "/topic/portfolio",
                payload
        );

        System.out.println("Portfolio pushed to WebSocket.");

    }

    public void publishPrice(String symbol,
                             double price) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("symbol", symbol);
        payload.put("price", price);
        payload.put("time", LocalDateTime.now());

        messagingTemplate.convertAndSend(
                "/topic/prices",
                payload
        );

    }

    public void publishTradeNotification(String message) {

        messagingTemplate.convertAndSend(
                "/topic/trades",
                message
        );

    }

    public void publishSystemStatus(String status) {

        messagingTemplate.convertAndSend(
                "/topic/system",
                status
        );

    }

}
