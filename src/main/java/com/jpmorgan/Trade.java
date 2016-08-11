package com.jpmorgan;

import java.util.Date;

/**
 * Created by doucampb on 08/08/2016.
 */
public class Trade {

    private Date timestamp;
    private TradeType tradeType;
    private int quantity;
    private double tradePrice;

    public Trade(Date timestamp, TradeType tradeType, int quantity, double tradePrice ) {
        this.timestamp = timestamp;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.tradePrice = tradePrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }
}
