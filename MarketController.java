package com.poojitha.trading.controller;

import com.poojitha.trading.kafka.MarketDataProducer;
import com.poojitha.trading.model.Trade;
import com.poojitha.trading.service.TradingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trading")
public class MarketController {

    private final TradingService tradingService;
    private final MarketDataProducer marketDataProducer;

    public MarketController(TradingService tradingService,
                            MarketDataProducer marketDataProducer) {
        this.tradingService = tradingService;
        this.marketDataProducer = marketDataProducer;
    }

    @PostMapping("/execute")
    public Trade executeTrade(@RequestParam String symbol,
                              @RequestParam double currentPrice,
                              @RequestParam double movingAverage,
                              @RequestParam int quantity,
                              @RequestParam String trader) {

        Trade trade = tradingService.executeTrade(
                symbol,
                currentPrice,
                movingAverage,
                quantity,
                trader
        );

        marketDataProducer.publishTrade(trade);

        return trade;
    }

    @GetMapping("/all")
    public List<Trade> getAllTrades() {
        return tradingService.getAllTrades();
    }

    @GetMapping("/{tradeId}")
    public Optional<Trade> getTrade(@PathVariable Long tradeId) {
        return tradingService.getTrade(tradeId);
    }

    @GetMapping("/symbol/{symbol}")
    public List<Trade> getTradesBySymbol(@PathVariable String symbol) {
        return tradingService.getTradesBySymbol(symbol);
    }

    @PutMapping("/{tradeId}/price")
    public String updatePrice(@PathVariable Long tradeId,
                              @RequestParam double latestPrice) {

        tradingService.updateTradePrice(tradeId, latestPrice);

        return "Trade price updated successfully.";
    }

    @DeleteMapping("/{tradeId}")
    public String cancelTrade(@PathVariable Long tradeId) {

        tradingService.cancelTrade(tradeId);

        return "Trade cancelled successfully.";
    }

    @GetMapping("/summary")
    public String summary() {
        return tradingService.getTradingSummary();
    }

    @DeleteMapping("/session")
    public String clearTradingSession() {

        tradingService.clearTradingSession();

        return "Trading session cleared successfully.";
    }

}
