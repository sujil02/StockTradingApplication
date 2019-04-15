import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.IPortfolioV2;
import model.Portfolio;
import model.TradeType;

import static org.junit.Assert.assertEquals;

/**
 * This class contains automated test cases for new features in portfolio in {@link
 * model.IPortfolioV2}.
 */
public class IPortfolioV2Test {
  @Test
  public void testMakeATradeWithCommission() {
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    int quantity = 100;
    IPortfolioV2 portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity, 0);
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity, 10);
    assertEquals("Google\tGOOG\t100\t118959.0\tFri Mar 15 12:00:00 EDT 2019\n" +
                    "Google\tGOOG\t100\t118969.0\tFri Mar 15 12:00:00 EDT 2019"
            , portfolio.getPortfolioContents());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithNegativeCommission() {
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    int quantity = 100;
    IPortfolioV2 portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity, -10);
  }

  @Test
  public void testMakeATradeWithZeroCommission() {
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    int quantity = 100;
    IPortfolioV2 portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity, 0);
    assertEquals("Google\tGOOG\t100\t118959.0\tFri Mar 15 12:00:00 EDT 2019"
            , portfolio.getPortfolioContents());
  }

  @Test
  public void testMakeATradeWithCommissionMultipleTransactions() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolioV2 portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol
            , companyName, quantity, 100F);
    tickerSymbol = "MSFT";
    companyName = "Microsoft";
    quantity = 500;
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-11 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol
            , companyName, quantity, 10f);

    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-18 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }

    assertEquals(175051.5, portfolio.getPortfolioCostBias(refDate), 0.01);

    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-13 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }

    assertEquals(55992.5, portfolio.getPortfolioCostBias(refDate), 0.01);
  }

  @Test
  public void testMakeATradeWithInvestmentAmount() {
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolioV2 portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName
            , 1189.59f, 0);
    assertEquals("Google\tGOOG\t1\t1189.59\tFri Mar 15 12:00:00 EDT 2019"
            , portfolio.getPortfolioContents());
  }

  @Test
  public void testMakeATradeWithInvestmentAmountGreaterThanExactValue() {
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolioV2 portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName
            , 119000f, 0);
    assertEquals("Google\tGOOG\t100\t118959.0\tFri Mar 15 12:00:00 EDT 2019"
            , portfolio.getPortfolioContents());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithInvestmentAmountLessThanSingleShareAmount() {
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolioV2 portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName
            , 100f, 0);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithInvestmentAmountZero() {
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolioV2 portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName
            , 0f, 0);
  }

  @Test
  public void testMakeATradeWithInvestmentAmountMultipleTransactions() {
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    int quantity = 100;
    IPortfolioV2 portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity, 10);

    refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName
            , 119000f, 0);
    assertEquals("Google\tGOOG\t100\t118969.0\tFri Mar 15 12:00:00 EDT 2019\n" +
                    "Google\tGOOG\t100\t118959.0\tFri Mar 15 12:00:00 EDT 2019"
            , portfolio.getPortfolioContents());

  }

  @Test
  public void testMakeAPortfolioAndExport() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolioV2 portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
    tickerSymbol = "MSFT";
    companyName = "Microsoft";
    quantity = 500;
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-11 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);

    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-18 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }

    assertEquals(174941.5, portfolio.getPortfolioCostBias(refDate), 0.01);

    portfolio.exportPortfolio(System.getProperty("user.dir") +
            System.getProperty("file.separator") + "res" +
            System.getProperty("file.separator") + "Portfolio JSON's" +
            System.getProperty("file.separator"));

    try {
      String fileData = new String(Files
              .readAllBytes(Paths.get(System.getProperty("user.dir") +
                      System.getProperty("file.separator") + "res" +
                      System.getProperty("file.separator") + "Portfolio JSON's" +
                      System.getProperty("file.separator") + "Default.json")));
      assertEquals("{\n" +
              "  \"portfolioName\": \"Default\",\n" +
              "  \"ownedStocks\": [\n" +
              "    {\n" +
              "      \"companyName\": \"Google\",\n" +
              "      \"tickerSymbol\": \"GOOG\",\n" +
              "      \"quantity\": 100,\n" +
              "      \"costPrice\": 1189.59,\n" +
              "      \"purchaseDate\": \"03/15/2019 12:00:00\",\n" +
              "      \"commission\": 0.0\n" +
              "    },\n" +
              "    {\n" +
              "      \"companyName\": \"Microsoft\",\n" +
              "      \"tickerSymbol\": \"MSFT\",\n" +
              "      \"quantity\": 500,\n" +
              "      \"costPrice\": 111.965,\n" +
              "      \"purchaseDate\": \"03/11/2019 12:00:00\",\n" +
              "      \"commission\": 0.0\n" +
              "    }\n" +
              "  ]\n" +
              "}", fileData);
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void testMakeAPortfolioAndExportWithNoTrades() {
    IPortfolioV2 portfolio = new Portfolio("DefaultNoTrade");
    portfolio.exportPortfolio(System.getProperty("user.dir") +
            System.getProperty("file.separator") + "res" +
            System.getProperty("file.separator") + "Portfolio JSON's" +
            System.getProperty("file.separator"));

    try {
      String fileData = new String(Files
              .readAllBytes(Paths.get(System.getProperty("user.dir") +
                      System.getProperty("file.separator") +
                      "res" + System.getProperty("file.separator") + "Portfolio JSON's" +
                      System.getProperty("file.separator") + "DefaultNoTrade.json")));
      assertEquals("{\n" +
              "  \"portfolioName\": \"DefaultNoTrade\",\n" +
              "  \"ownedStocks\": []\n" +
              "}", fileData);
    } catch (IOException e) {
      Assert.fail();
    }
  }

}