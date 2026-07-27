package com.poojitha.trading.service;

import com.poojitha.trading.model.Portfolio;
import com.poojitha.trading.model.Trade;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PortfolioService {

    private final Map<String, Portfolio> portfolios = new HashMap<>();

    public Portfolio processTrade(Trade trade) {

        Portfolio portfolio = portfolios.computeIfAbsent(
                trade.getTrader(),
                Portfolio::new
        );

        if (trade.isBuyTrade()) {

            portfolio.buyStock(
                    trade.getSymbol(),
                    trade.getQuantity(),
                    trade.getMarketPrice()
            );

        } else if (trade.isSellTrade()) {

            portfolio.sellStock(
                    trade.getSymbol(),
                    trade.getQuantity(),
                    trade.getMarketPrice()
            );
        }

        return portfolio;
    }

    public Portfolio getPortfolio(String trader) {

        return portfolios.get(trader);

    }

    public Map<String, Portfolio> getAllPortfolios() {

        return portfolios;

    }

    public void updatePrice(String symbol,
                            double oldPrice,
                            double newPrice) {

        portfolios.values().forEach(
                portfolio -> portfolio.updateMarketValue(
                        symbol,
                        oldPrice,
                        newPrice
                )
        );
    }

    public double getOverallInvestment() {

        return portfolios.values()
                .stream()
                .mapToDouble(Portfolio::getInvestedAmount)
                .sum();

    }

    public double getOverallCurrentValue() {

        return portfolios.values()
                .stream()
                .mapToDouble(Portfolio::getCurrentValue)
                .sum();

    }

    public double getOverallProfitLoss() {

        return getOverallCurrentValue()
                - getOverallInvestment();

    }

    public void clear() {

        portfolios.clear();

    }

}
