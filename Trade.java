package com.poojitha.trading.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Trade {

    private Long tradeId;
    private String symbol;
    private double marketPrice;
    private int quantity;
    private String action;
    private double totalAmount;
    private String trader;
    private String status;
    private LocalDateTime tradeTime;

    public Trade() {
    }

    public Trade(Long tradeId,
                 String symbol,
                 double marketPrice,
                 int quantity,
                 String action,
                 String trader) {

        this.tradeId = tradeId;
        this.symbol = symbol;
        this.marketPrice = marketPrice;
        this.quantity = quantity;
        this.action = action;
        this.trader = trader;
        this.tradeTime = LocalDateTime.now();
        this.status = "EXECUTED";
        calculateTradeAmount();
    }

    public void calculateTradeAmount() {
        this.totalAmount = this.marketPrice * this.quantity;
    }

    public boolean isBuyTrade() {
        return "BUY".equalsIgnoreCase(action);
    }

    public boolean isSellTrade() {
        return "SELL".equalsIgnoreCase(action);
    }

    public void updateMarketPrice(double latestPrice) {
        this.marketPrice = latestPrice;
        calculateTradeAmount();
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
        calculateTradeAmount();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTradeAmount();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(LocalDateTime tradeTime) {
        this.tradeTime = tradeTime;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", symbol='" + symbol + '\'' +
                ", marketPrice=" + marketPrice +
                ", quantity=" + quantity +
                ", action='" + action + '\'' +
                ", totalAmount=" + totalAmount +
                ", trader='" + trader + '\'' +
                ", status='" + status + '\'' +
                ", tradeTime=" + tradeTime +
                '}';
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof Trade)) {
            return false;
        }

        Trade trade = (Trade) object;

        return Objects.equals(tradeId, trade.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId);
    }

}
