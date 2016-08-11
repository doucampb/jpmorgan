import com.jpmorgan.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by doucampb on 08/08/2016.
 */
public class StockTest {

    @Test
    public void calculateDividendYieldTest() {

        Stock commonStock = new Stock("POP", StockType.COMMON, 8, 0, 100);
        Stock preferredStock = new Stock("GIN", StockType.PREFERRED, 8, 2, 100);

        // Calculate and check the dividend yield for common stock type
        double marketPrice = 100.0;
        double dividendYield = commonStock.calculateDividendYield(marketPrice);
        double expectedResult = commonStock.getLastDividend() / marketPrice;

        // double delta (0) means we want 100% precision
        assertEquals(expectedResult, dividendYield, 0);

        // Calculate and check the dividend yield for preferred stock type
        marketPrice = 200.0;
        dividendYield = preferredStock.calculateDividendYield(marketPrice);
        expectedResult = (preferredStock.getFixedDividend() * preferredStock.getParValue())/marketPrice;

        assertEquals(expectedResult, dividendYield, 0);

    }

    @Test
    public void calculatePERatioTest() {

        //The definition of PE ratio is market price / dividend
        //is it fair to assume that this is the last dividend?

        Stock stock = new Stock("ALE", StockType.COMMON, 23, 0, 60);

        double marketPrice = 100.0;
        double actualPeRatio = stock.calculatePERatio(marketPrice);
        double expectedResult = marketPrice/stock.getLastDividend();


        assertEquals(expectedResult, actualPeRatio, 0);

    }

    @Test
    public void recordTradeTest() {

        Stock stock = new Stock("ALE", StockType.COMMON, 23, 0, 60);

        // Having made no trades list should be empty
        assertEquals(0, stock.getTradesList().size(), 0);

        // Make trade
        stock.recordTrade(500,100,TradeType.BUY);

        //One Trade recorded so list should be of size 1
        assertEquals(1, stock.getTradesList().size(), 0);

        for (Trade t : stock.getTradesList()) {
            assertEquals(500, t.getQuantity(), 0);
            assertEquals(100, t.getTradePrice(), 0);
            assertEquals(TradeType.BUY, t.getTradeType());
        }
    }

    @Test
    public void calculateVolumeWeightedStockTest() {

        Stock stock = new Stock("ALE", StockType.COMMON, 23, 0, 60);

        // Make trade
        stock.recordTrade(2,10,TradeType.BUY);
        // Make trade
        stock.recordTrade(2,10,TradeType.BUY);


        double volumeWeightedStockPrice = stock.calculateVolumeWeightedStockPrice();

        // (2*10) + (2*10) / (2+2)
        assertEquals(10, volumeWeightedStockPrice, 0);


        // Now add a record 30 minutes ago and check that the filtering is working in which case
        // the result should be the same as before
        Date startTime = new Date(new Date().getTime() - (30 * 60 * 1000));
        stock.getTradesList().add(new Trade(startTime, TradeType.BUY, 1, 20.0));
        volumeWeightedStockPrice = stock.calculateVolumeWeightedStockPrice();
        assertEquals(10.0, volumeWeightedStockPrice, 0.0);
    }

    @Test
    public void calculateGeometricMean() {

        List<Stock> stockList = new ArrayList<>();

        Stock stock = new Stock("ALE", StockType.COMMON, 23, 0, 60);
        Stock stock2 = new Stock("TEST1", StockType.COMMON, 23, 0, 60);

        stock.recordTrade(500,100,TradeType.BUY);
        stock.recordTrade(200,200,TradeType.BUY);

        stock2.recordTrade(800, 300, TradeType.SELL);

        stockList.add(stock);
        stockList.add(stock2);

        // Should be cube root of (300*200*100) =  cube root of (6000000) 181.71??

        assertEquals(181.7120592832139, StockUtils.calculateGeometricMean(stockList), 0);



    }



}
