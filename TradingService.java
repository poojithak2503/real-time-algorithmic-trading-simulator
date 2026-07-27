package com.poojitha.trading.service;

import com.poojitha.trading.model.Trade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TradingService {

    private final List<Trade> trades = new ArrayList<>();

    public Trade executeTrade(String symbol,
                              double currentPrice,
                              double movingAverage,
                              int quantity,
                              String trader) {

        String action = determineAction(currentPrice, movingAverage);

        Trade trade = new Trade(
                System.currentTimeMillis(),
                symbol,
                currentPrice,
                quantity,
                action,
                trader
        );

        trades.add(trade);

        return trade;
    }

    public String determineAction(double currentPrice,
                                  double movingAverage) {

        if (currentPrice > movingAverage) {
            return "SELL";
        }

        if (currentPrice < movingAverage) {
            return "BUY";
        }

        return "HOLD";
    }

    public List<Trade> getAllTrades() {
        return new ArrayList<>(trades);
    }

    public Optional<Trade> getTrade(Long tradeId) {

        return trades.stream()
                .filter(trade -> trade.getTradeId().equals(tradeId))
                .findFirst();
    }

    public List<Trade> getTradesBySymbol(String symbol) {

        return trades.stream()
                .filter(trade -> trade.getSymbol().equalsIgnoreCase(symbol))
                .toList();
    }

    public double getTotalTradeValue() {

        return trades.stream()
                .mapToDouble(Trade::getTotalAmount)
                .sum();
    }

    public long getBuyTradeCount() {

        return trades.stream()
                .filter(Trade::isBuyTrade)
                .count();
    }

    public long getSellTradeCount() {

        return trades.stream()
                .filter(Trade::isSellTrade)
                .count();
    }

    public Optional<Trade> getLargestTrade() {

        return trades.stream()
                .max(Comparator.comparingDouble(Trade::getTotalAmount));
    }

    public void updateTradePrice(Long tradeId,
                                 double latestPrice) {

        getTrade(tradeId)
                .ifPresent(trade -> trade.updateMarketPrice(latestPrice));
    }

    public void cancelTrade(Long tradeId) {

        getTrade(tradeId)
                .ifPresent(trade -> trade.setStatus("CANCELLED"));
    }

    public List<Trade> getExecutedTrades() {

        return trades.stream()
                .filter(trade -> "EXECUTED".equalsIgnoreCase(trade.getStatus()))
                .toList();
    }

    public List<Trade> getCancelledTrades() {

        return trades.stream()
                .filter(trade -> "CANCELLED".equalsIgnoreCase(trade.getStatus()))
                .toList();
    }

    public void clearTradingSession() {
        trades.clear();
    }

    public String getTradingSummary() {

        StringBuilder summary = new StringBuilder();

        summary.append("Trading Session Summary\n");
        summary.append("------------------------------\n");
        summary.append("Generated At : ").append(LocalDateTime.now()).append("\n");
        summary.append("Total Trades : ").append(trades.size()).append("\n");
        summary.append("BUY Trades   : ").append(getBuyTradeCount()).append("\n");
        summary.append("SELL Trades  : ").append(getSellTradeCount()).append("\n");
        summary.append("Trade Value  : ").append(getTotalTradeValue()).append("\n");

        getLargestTrade().ifPresent(trade ->
                summary.append("Largest Trade: ")
                        .append(trade.getSymbol())
                        .append(" - ")
                        .append(trade.getTotalAmount())
                        .append("\n")
        );

        return summary.toString();
    }

}
