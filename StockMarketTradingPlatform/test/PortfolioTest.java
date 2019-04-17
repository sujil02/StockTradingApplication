import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.IPortfolio;
import model.Portfolio;
import model.TradeType;

import static org.junit.Assert.assertEquals;


/**
 * This class implements automated test cases for validation of {@link IPortfolio} interface.
 */
public class PortfolioTest {
  @Test
  public void testPortfolioConstructor() {
    IPortfolio portfolio = new Portfolio("Default");
    assertEquals("Default", portfolio.getPortfolioName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioConstructorWithEmptyString() {
    IPortfolio portfolio = new Portfolio("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioConstructorWithNull() {
    IPortfolio portfolio = new Portfolio("");
  }

  @Test
  public void testMakeATrade() {
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
    IPortfolio portfolio = new Portfolio("Default");
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
    assertEquals("Google\tGOOG\t100\t118446.0\tFri Mar 15 12:00:00 EDT 2019"
            , portfolio.getPortfolioContents());
  }

  @Test
  public void testMakeATradeMultipleWithSameCompany() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
    quantity = 500;
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-11 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);

    assertEquals("Google\tGOOG\t100\t118446.0\tFri Mar 15 12:00:00 EDT 2019\n" +
                    "Google\tGOOG\t500\t587880.0\tMon Mar 11 12:00:00 EDT 2019"
            , portfolio.getPortfolioContents());
  }

  @Test
  public void testMakeATradeMultipleWithDifferentCompanies() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
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

    assertEquals("Google\tGOOG\t100\t118446.0\tFri Mar 15 12:00:00 EDT 2019\n" +
                    "Microsoft\tMSFT\t500\t56415.0\tMon Mar 11 12:00:00 EDT 2019"
            , portfolio.getPortfolioContents());
  }

  @Test
  public void testGetContents() {

    IPortfolio portfolio = new Portfolio("Default");
    assertEquals(""
            , portfolio.getPortfolioContents());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithFutureDate() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2020-03-20 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithHoliday() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithBeforeMarketOpening() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 07:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithAfterMarketOpening() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 20:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithInvalidTickerSymbol() {
    String tickerSymbol = "zzzz";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 20:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithEmptyTickerSymbol() {
    String tickerSymbol = "";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 20:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithNullTickerSymbol() {
    String tickerSymbol = null;
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 20:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithZeroQuantity() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 0;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 20:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeATradeWithNegativeQuantity() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = -23;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 20:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
  }

  @Test
  public void testGetCostBias() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
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

    assertEquals(174861.0, portfolio.getPortfolioCostBias(refDate), 0.01);

    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-13 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }

    assertEquals(56415.0, portfolio.getPortfolioCostBias(refDate), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCostBiasWithNull() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
    portfolio.getPortfolioCostBias(null);
  }

  @Test
  public void testGetValue() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
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

    assertEquals(177211.0, portfolio.getPortfolioValue(refDate), 0.01);

    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-13 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }

    assertEquals(57250.0, portfolio.getPortfolioValue(refDate), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueWithNull() {
    String tickerSymbol = "GOOG";
    String companyName = "Google";
    IPortfolio portfolio = new Portfolio("Default");
    int quantity = 100;
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    portfolio.makeATrade(TradeType.BUY, refDate, tickerSymbol, companyName, quantity);
    portfolio.getPortfolioValue(null);
  }


}