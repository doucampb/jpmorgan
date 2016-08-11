package com.jpmorgan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by doucampb on 07/08/2016.
 */
public class Stock {

    private String stockSymbol;
    private StockType stockType;
    private double lastDividend;
    private double fixedDividend;
    private double parValue;
    private List<Trade> tradesList = new ArrayList<>();


    public Stock(String stockSymbol, StockType stockType, double lastDividend, double fixedDividend, double parValue) {
        this.stockSymbol = stockSymbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }


    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public StockType getStockType() {
        return stockType;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public double getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public double getParValue() {
        return parValue;
    }

    public void setParValue(double parValue) {
        this.parValue = parValue;
    }


    public double calculateDividendYield(double marketPrice) {

        if (marketPrice > 0.0) {

            switch (this.getStockType()) {

                case COMMON:
                    return this.getLastDividend() / marketPrice;

                case PREFERRED:
                    return this.getFixedDividend() * this.getParValue() / marketPrice;

                default:
                    return -1;
            }
        }

        return -1;
    }


    public double calculatePERatio(Double price) {

        if (price > 0.0) {
            return price / this.getLastDividend();
        }

        return -1;
    }

    public void recordTrade(int quantity, double tradePrice, TradeType tradeType) {

        Trade trade = null;

        switch(tradeType) {
            case BUY:
                trade = new Trade(new Date(), TradeType.BUY, quantity, tradePrice);
                tradesList.add(trade);
                break;
            case SELL:
                trade = new Trade(new Date(), TradeType.SELL, quantity, tradePrice);
                tradesList.add(trade);
                break;
        }
    }

    public double calculateVolumeWeightedStockPrice() {

        //Time 15 Minutes ago
        final Date timeLimit = new Date(new Date().getTime() - (15 * 60 * 1000));

        List<Trade> filteredList = tradesList.stream().filter(t -> t.getTimestamp().after(timeLimit)).collect(Collectors.toList());

        Double volumeWeigthedStockPrice = 0.0;
        int quantityTotal = 0;
        for (Trade trade : filteredList) {
            quantityTotal += trade.getQuantity();
            volumeWeigthedStockPrice += trade.getTradePrice() * trade.getQuantity();
        }

        return volumeWeigthedStockPrice/quantityTotal;
    }

    public List<Trade> getTradesList() {
        return tradesList;
    }

    public void setTradesList(List<Trade> tradesList) {
        this.tradesList = tradesList;
    }
}
