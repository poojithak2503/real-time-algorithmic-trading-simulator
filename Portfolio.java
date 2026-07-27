package com.poojitha.trading.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Portfolio {

    private String trader;
    private Map<String, Integer> holdings = new HashMap<>();
    private double investedAmount;
    private double currentValue;

    public Portfolio() {
    }

    public Portfolio(String trader) {
        this.trader = trader;
    }

    public void buyStock(String symbol, int quantity, double price) {

        holdings.put(symbol,
                holdings.getOrDefault(symbol, 0) + quantity);

        investedAmount += quantity * price;
        currentValue += quantity * price;
    }

    public void sellStock(String symbol, int quantity, double price) {

        if (!holdings.containsKey(symbol)) {
            return;
        }

        int available = holdings.get(symbol);

        if (available < quantity) {
            quantity = available;
        }

        holdings.put(symbol, available - quantity);

        if (holdings.get(symbol) == 0) {
            holdings.remove(symbol);
        }

        currentValue -= quantity * price;
    }

    public void updateMarketValue(String symbol,
                                  double oldPrice,
                                  double newPrice) {

        Integer quantity = holdings.get(symbol);

        if (quantity == null) {
            return;
        }

        currentValue -= quantity * oldPrice;
        currentValue += quantity * newPrice;
    }

    public double calculateProfitLoss() {
        return currentValue - investedAmount;
    }

    public int totalStocks() {
        return holdings.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Set<String> getSymbols() {
        return holdings.keySet();
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public void setHoldings(Map<String, Integer> holdings) {
        this.holdings = holdings;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public double getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    public String toString() {

        return "Portfolio{" +
                "trader='" + trader + '\'' +
                ", holdings=" + holdings +
                ", investedAmount=" + investedAmount +
                ", currentValue=" + currentValue +
                ", profitLoss=" + calculateProfitLoss() +
                '}';
    }

}
