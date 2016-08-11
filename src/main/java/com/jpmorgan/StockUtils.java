package com.jpmorgan;

import java.util.List;

/**
 * Created by doucampb on 10/08/2016.
 */
public class StockUtils {

    public static double calculateGeometricMean(List<Stock> stocks) {

        double sum = 1;

        if (stocks.isEmpty()) {
            return -1;
        }

        int counter = 0;
        for (Stock stock : stocks) {
            for (int i = 0; i < stock.getTradesList().size(); i++) {
                sum *= stock.getTradesList().get(i).getTradePrice();
                counter++;
            }
        }

        return Math.pow(sum, (double) 1 / counter);

    }

}
