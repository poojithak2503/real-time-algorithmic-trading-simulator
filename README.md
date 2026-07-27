# Real-Time Algorithmic Trading Simulator

## Overview

The Real-Time Algorithmic Trading Simulator is a Java Full Stack application that simulates stock market trading using an event-driven architecture. The application processes market data, evaluates trading decisions using a simple moving average strategy, publishes events through Apache Kafka, and provides real-time portfolio updates using WebSockets.

The project demonstrates the implementation of modern enterprise application development concepts including REST APIs, event-driven communication, service-oriented architecture, Kafka messaging, WebSocket communication, and Spring Boot.

---

## Features

- Real-time stock trade simulation
- Buy, Sell and Hold decision engine
- Event-driven architecture using Apache Kafka
- RESTful API implementation
- Real-time portfolio updates through WebSockets
- Portfolio management
- Trade history management
- Trading summary generation
- Price update simulation
- In-memory data management

---

## Technology Stack

| Technology | Version |
|------------|----------|
| Java | 17 |
| Spring Boot | 3.x |
| Apache Kafka | Latest |
| Spring WebSocket | Latest |
| Maven | 3.x |
| REST API | Spring MVC |

---

## Project Structure

```
real-time-algorithmic-trading-simulator
│
├── controller
│     MarketController.java
│
├── kafka
│     MarketDataProducer.java
│     MarketDataConsumer.java
│
├── model
│     Trade.java
│     Portfolio.java
│
├── service
│     TradingService.java
│     PortfolioService.java
│
├── websocket
│     WebSocketConfig.java
│     PortfolioWebSocketService.java
│
└── TradingApplication.java
```

---

## Application Flow

```
                    Stock Price

                         │

                         ▼

                MarketController

                         │

                         ▼

                TradingService

                         │

      Determines BUY / SELL / HOLD

                         │

                         ▼

                 Creates Trade

                         │

                         ▼

             MarketDataProducer

                         │

                  Apache Kafka

                         │

                         ▼

             MarketDataConsumer

                         │

                         ▼

              PortfolioService

                         │

                         ▼

       PortfolioWebSocketService

                         │

                  WebSocket

                         │

                         ▼

                Live Dashboard
```

---

## Trading Workflow

1. User submits a trade request.
2. MarketController receives the request.
3. TradingService evaluates the stock price.
4. TradingService determines BUY, SELL or HOLD.
5. Trade object is created.
6. Trade is published to Kafka.
7. Kafka Consumer receives the event.
8. PortfolioService updates the trader portfolio.
9. Portfolio is pushed to clients using WebSocket.
10. Client dashboard receives the latest portfolio.

---

## Trading Strategy

The simulator follows a Moving Average based strategy.

### BUY

```
Current Price < Moving Average
```

### SELL

```
Current Price > Moving Average
```

### HOLD

```
Current Price == Moving Average
```

---

## REST APIs

### Execute Trade

```
POST /api/trading/execute
```

Parameters

```
symbol
currentPrice
movingAverage
quantity
trader
```

Example

```
POST /api/trading/execute

symbol=AAPL
currentPrice=185.25
movingAverage=180
quantity=20
trader=Poojitha
```

---

### Get All Trades

```
GET /api/trading/all
```

---

### Get Trade

```
GET /api/trading/{tradeId}
```

---

### Get Trades By Symbol

```
GET /api/trading/symbol/{symbol}
```

---

### Update Market Price

```
PUT /api/trading/{tradeId}/price
```

---

### Cancel Trade

```
DELETE /api/trading/{tradeId}
```

---

### Trading Summary

```
GET /api/trading/summary
```

---

### Clear Trading Session

```
DELETE /api/trading/session
```

---

## Kafka Topics

### market-data-topic

Used for publishing completed trades.

Example

```
{
    "symbol":"AAPL",
    "marketPrice":185.40,
    "quantity":20,
    "action":"BUY"
}
```

---

### market-price-topic

Used for publishing live stock prices.

Example

```
AAPL,186.10
```

---

### system-events

Used for application notifications.

Example

```
Trading session started
```

---

## WebSocket Endpoints

### Connection

```
/market-feed
```

### Portfolio Updates

```
/topic/portfolio
```

### Live Prices

```
/topic/prices
```

### Trade Notifications

```
/topic/trades
```

### System Status

```
/topic/system
```

---

## Business Components

### MarketController

Responsible for exposing REST endpoints and handling incoming trading requests.

---

### TradingService

Contains the business logic for evaluating stock prices and executing trades.

---

### MarketDataProducer

Publishes trade events to Apache Kafka.

---

### MarketDataConsumer

Consumes Kafka events and updates market information.

---

### PortfolioService

Maintains trader portfolios and calculates investment values.

---

### PortfolioWebSocketService

Pushes live portfolio updates to connected clients.

---

## Sample Trading Session

```
Trader : Poojitha

Stock : AAPL

Current Price : 184.50

Moving Average : 180.20

Decision : SELL

Quantity : 25

Trade Value : 4612.50
```

---

## Future Enhancements

- Spring Security with JWT Authentication
- PostgreSQL Integration
- Spring Data JPA
- Redis Cache
- Docker Support
- Kubernetes Deployment
- AWS Deployment
- React Dashboard
- Multiple Trading Strategies
- Historical Market Data
- Portfolio Analytics
- Profit and Loss Dashboard
- Email Notifications
- SMS Notifications
- Kafka Streams
- Trade Audit Logs
- Unit Testing using JUnit
- Integration Testing
- Swagger Documentation

---

## Learning Outcomes

This project demonstrates practical implementation of

- Spring Boot application development
- RESTful Web Services
- Event-driven architecture
- Apache Kafka messaging
- WebSocket communication
- Service Layer design
- Business logic implementation
- Portfolio management
- Java Collections Framework
- Object-Oriented Programming
- Enterprise application architecture

---

## Author

**Poojitha Kanuri**

Java Full Stack Developer

Email: poojithakanuri03@gmail.com

LinkedIn: https://linkedin.com/in/poojithakanuri

GitHub: https://github.com/poojithak2503
