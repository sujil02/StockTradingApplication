import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.IController;
import controller.TextController;
import model.IUserV2;
import view.IView;
import view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class implements automated test cases for validation of {@link IController} interface.
 */
public class IControllerTest {
  @Test
  public void testCreatePortfolioFromController() {
    StringBuffer out = new StringBuffer();
    String input = "1\n"
            + "Portfolio test\n"
            + "3\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    float unicode = 3.14f;
    String testString = "output test";
    StringBuilder log = new StringBuilder();
    IUserV2 mockUser = new MockUser(unicode, testString, log);
    IController controller = new TextController(mockUser, view);
    controller.start();
    assertEquals("PortfolioName Entered: Portfolio test\n", log.toString());
  }

  @Test
  public void testGetPortfolioNamesFromController() {
    StringBuffer out = new StringBuffer();
    String input = "1\n"
            + "Portfolio test\n"
            + "2\n"
            + "test\n"
            + "6\n"
            + "3\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    float unicode = 3.14f;
    String testString = "output test";
    StringBuilder log = new StringBuilder();
    IUserV2 mockUser = new MockUser(unicode, testString, log);
    IController controller = new TextController(mockUser, view);
    controller.start();
    assertEquals("PortfolioName Entered: Portfolio test\n", log.toString());
    assertTrue(out.toString().contains(testString));
  }

  @Test
  public void testMakeATradeFromController() {
    StringBuffer out = new StringBuffer();
    String input = "1\n"
            + "Portfolio test\n"
            + "2\n"
            + "Portfolio test\n"
            + "2\n"
            + "Google\n"
            + "GOOG\n"
            + "100\n"
            + "03/11/2019 12:00:00\n"
            + "6\n"
            + "3\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    float unicode = 3.14f;
    String testString = "output test";
    StringBuilder log = new StringBuilder();
    IUserV2 mockUser = new MockUser(unicode, testString, log);
    IController controller = new TextController(mockUser, view);
    controller.start();
    assertEquals("PortfolioName Entered: Portfolio test\n" +
            "PortfolioName: Portfolio test, TradeType: BUY, Date: Mon Mar 11 12:00:00 EDT 2019, " +
            "TickerSymbol: GOOG, CompanyName: Google, Quantity: 100\n", log.toString());
    assertTrue(out.toString().contains("Trade completed successfully."));
    assertTrue(out.toString().contains(testString));
  }

  @Test
  public void testGetPortfolioContentsFromController() {
    StringBuffer out = new StringBuffer();
    String input = "1\n"
            + "Portfolio test\n"
            + "2\n"
            + "Portfolio test\n"
            + "1\n"
            + "6\n"
            + "3\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    float unicode = 3.14f;
    String testString = "output test";
    StringBuilder log = new StringBuilder();
    IUserV2 mockUser = new MockUser(unicode, testString, log);
    IController controller = new TextController(mockUser, view);
    controller.start();
    assertEquals("PortfolioName Entered: Portfolio test\n" +
            "PortfolioName Entered: Portfolio test\n", log.toString());
    int count = (out.toString().split(testString, -1).length) - 1;
    assertEquals(2, count);
  }

  @Test
  public void testGetCostBiasFromController() {
    StringBuffer out = new StringBuffer();
    String input = "1\n"
            + "Portfolio test\n"
            + "2\n"
            + "Portfolio test\n"
            + "3\n"
            + "03/13/2019\n"
            + "6\n"
            + "3\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    float unicode = 3.14f;
    String testString = "output test";
    StringBuilder log = new StringBuilder();
    IUserV2 mockUser = new MockUser(unicode, testString, log);
    IController controller = new TextController(mockUser, view);
    controller.start();
    assertEquals("PortfolioName Entered: Portfolio test\n" +
                    "PortfolioName Entered: Portfolio test, refDate: Wed Mar 13 00:00:00 EDT 2019\n"
            , log.toString());
    assertTrue(out.toString().contains("Cost Bias:3.14"));

  }

  @Test
  public void testGetValueFromController() {
    StringBuffer out = new StringBuffer();
    String input = "1\n"
            + "Portfolio test\n"
            + "2\n"
            + "Portfolio test\n"
            + "4\n"
            + "03/13/2019\n"
            + "6\n"
            + "3\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    float unicode = 3.14f;
    String testString = "output test";
    StringBuilder log = new StringBuilder();
    IUserV2 mockUser = new MockUser(unicode, testString, log);
    IController controller = new TextController(mockUser, view);
    controller.start();
    assertEquals("PortfolioName Entered: Portfolio test\n" +
                    "PortfolioName Entered: Portfolio test, refDate: Wed Mar 13 00:00:00 EDT 2019\n"
            , log.toString());
    assertTrue(out.toString().contains("Value:3.14"));

  }

}