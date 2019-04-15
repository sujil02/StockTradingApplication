import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.IStock;
import model.Stock;

import static org.junit.Assert.assertEquals;


/**
 * This class implements automated test cases for validation of {@link IStock} interface.
 */
public class IStockTest {

  @Test
  public void testStockConstructor() {
    IStock stock = new Stock("Microsoft", "MSFT", 10, new Date(), 0);
    assertEquals("Microsoft", stock.getCompanyName());
    assertEquals("MSFT", stock.getTickerSymbol());
  }

  @Test
  public void testStockConstructorWithEmptyCompanyName() {
    IStock stock = new Stock("", "GOOG", 10, new Date(), 0);
    assertEquals("", stock.getCompanyName());
    assertEquals("GOOG", stock.getTickerSymbol());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStockConstructorWithEmptyTickerSymbol() {
    IStock stock = new Stock("Google", "", 10, new Date(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStockConstructorWithNullCompanyName() {
    IStock stock = new Stock(null, null, 10, new Date(), 0);
  }

  @Test
  public void testGetValue() {
    IStock stock = new Stock("Google", "GOOG", 10, new Date(), 0);
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-03-15 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    float value = stock.getValue(refDate);
    assertEquals(1189.58, value, 0.01);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueWithFutureDate() {
    IStock stock = new Stock("Google", "GOOG", 10, new Date(), 0);
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2020-03-20 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    float value = stock.getValue(refDate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueWithNullDate() {
    IStock stock = new Stock("Google", "GOOG", 10, new Date(), 0);
    float value = stock.getValue(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueWithHolidayDate() {
    IStock stock = new Stock("Google", "GOOG", 10, new Date(), 0);
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-01 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    float value = stock.getValue(refDate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueWithInvalidTickerSymbol() {
    IStock stock = new Stock("", "ZZZZ", 10, new Date(), 0);
    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              .parse("2019-01-02 12:00:00");
    } catch (ParseException e) {
      //do nothing
    }
    float value = stock.getValue(refDate);
  }

  @Test
  public void testStockIsEqual() {
    IStock stock1 = new Stock("", "ABCD", 10, new Date(), 0);
    IStock stock2 = new Stock("ABCD Corp.", "ABCD", 10, new Date(), 0);
    IStock stock3 = stock1;
    assertEquals(stock1, stock3);
    assertEquals(stock1, stock2);
    assertEquals(stock2, stock1);
  }

  @Test
  public void testHashCode() {
    IStock stock1 = new Stock("", "ABCD", 10, new Date(), 0);
    IStock stock2 = new Stock("ABCD Corp.", "ABCD", 10, new Date(), 0);
    assertEquals(stock1.hashCode(), stock2.hashCode());
    assertEquals(stock2.hashCode(), stock1.hashCode());
  }
}