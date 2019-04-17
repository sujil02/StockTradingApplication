import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.IController;
import controller.TextController;
import model.IUserV2;
import model.UserImpl;
import view.IView;
import view.View;

import static org.junit.Assert.assertTrue;

/**
 * This class contains automated test cases which tests Strategy feature using text based
 * controller.
 */
public class IStrategyTest {

  @Test
  public void testCreationAndExecutionOfStrategyOnNewPortfolioWithWeights() {
    StringBuffer out = new StringBuffer();
    String input = "3\n1\ndefault\ngoog 40\nmsft 30\namzn 20\nfb 10\ndone\n100000\n100\n" +
            "03/11/2019\n04/15/2019\n25\n3\n2\ndefault\n1\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t34\t$1175.76\tMon Mar 11 16:00:00 EDT 2019" +
            "\t$100.0\t\n" +
            "\tMSFT\t265\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t58\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t11\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t33\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t250\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t56\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t10\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t"));
  }

  @Test
  public void testCreationAndExecutionOfStrategyWithoutGivingWeights() {
    StringBuffer out = new StringBuffer();
    String input = "3\n1\ndefault\ngoog\nmsft\namzn\nfb\ndone\n100000\n100\n" +
            "03/11/2019\n04/15/2019\n25\n3\n2\ndefault\n1\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t21\t$1175.76\tMon Mar 11 16:00:00 EDT 2019\t" +
            "$100.0\t\n" +
            "\tMSFT\t221\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t145\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t20\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t208\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t142\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t13\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0"));
  }

  @Test
  public void testCreationAndExecutionOfStrategyWithoutGivingEndDate() {
    StringBuffer out = new StringBuffer();
    String input = "3\n1\ndefault\ngoog\nmsft\namzn\nfb\ndone\n100000\n100\n" +
            "03/11/2019\n\n25\n3\n2\ndefault\n1\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t21\t$1175.76\tMon Mar 11 16:00:00 EDT 2019\t" +
            "$100.0\t\n" +
            "\tMSFT\t221\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t145\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t20\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t208\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t142\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t13\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0"));
  }

  @Test
  public void testCreationAndExecutionOfStrategyWithNoRepetition() {
    StringBuffer out = new StringBuffer();
    String input = "3\n1\ndefault\ngoog\nmsft\namzn\nfb\ndone\n100000\n100\n" +
            "03/11/2019\n03/15/2019\n0\n3\n2\ndefault\n1\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t21\t$1175.76\tMon Mar 11 16:00:00 EDT 2019\t" +
            "$100.0\t\n" +
            "\tMSFT\t221\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t145\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t20\t$1193.2\tTue Mar 12 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t220\t$113.62\tTue Mar 12 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t145\t$171.92\tTue Mar 12 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1673.1\tTue Mar 12 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t20\t$1193.32\tWed Mar 13 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t218\t$114.5\tWed Mar 13 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t144\t$173.37\tWed Mar 13 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1690.81\tWed Mar 13 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t21\t$1185.55\tThu Mar 14 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t218\t$114.59\tThu Mar 14 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t146\t$170.17\tThu Mar 14 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1686.22\tThu Mar 14 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t21\t$1184.46\tFri Mar 15 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t215\t$115.91\tFri Mar 15 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t150\t$165.98\tFri Mar 15 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1712.36\tFri Mar 15 16:00:00 EDT 2019\t$100.0"));
  }

  @Test
  public void testCostBiasOfPortfolioAfterApplyingStrategy() {
    StringBuffer out = new StringBuffer();
    String input = "3\n1\ndefault\ngoog\nmsft\namzn\nfb\ndone\n100000\n100\n" +
            "03/11/2019\n\n25\n3\n2\ndefault\n1\n3\n04/17/2019\n3\n04/01/2019\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    String latestCostBias = "Cost Bias:$196682.22";
    String previousCostBias = "Cost Bias:$98365.22";
    assertTrue(out.toString().contains("GOOG\t21\t$1175.76\tMon Mar 11 16:00:00 EDT 2019\t" +
            "$100.0\t\n" +
            "\tMSFT\t221\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t145\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t20\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t208\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t142\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t13\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0"));
    assertTrue(out.toString().contains(previousCostBias));
    assertTrue(out.toString().contains(latestCostBias));
    assertTrue(out.toString().indexOf(latestCostBias) < out.toString()
            .indexOf(previousCostBias));
  }

  @Test
  public void testValueOfPortfolioAfterApplyingStrategy() {
    StringBuffer out = new StringBuffer();
    String input = "3\n1\ndefault\ngoog\nmsft\namzn\nfb\ndone\n100000\n100\n" +
            "03/11/2019\n\n25\n3\n2\ndefault\n1\n4\n04/17/2019\n4\n04/01/2019\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    String latestValue = "Value:$204457.75";
    String previousValue = "Value:$101246.61";
    assertTrue(out.toString().contains("GOOG\t21\t$1175.76\tMon Mar 11 16:00:00 EDT 2019\t" +
            "$100.0\t\n" +
            "\tMSFT\t221\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t145\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t20\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t208\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t142\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t13\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0"));
    assertTrue(out.toString().contains(previousValue));
    assertTrue(out.toString().contains(latestValue));
    assertTrue(out.toString().indexOf(latestValue) < out.toString()
            .indexOf(previousValue));
  }

  @Test
  public void testCreationAndExecutionOfStrategyOnExistingPortfolioWithWeights() {
    StringBuffer out = new StringBuffer();
    String input = "1\ndefault\n2\ndefault\n2\n2\n1\ngoog 40\nmsft 30\namzn 20\nfb 10\ndone" +
            "\n100000\n100\n03/11/2019\n04/15/2019\n25\n3\n3\n1\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t34\t$1175.76\tMon Mar 11 16:00:00 EDT 2019" +
            "\t$100.0\t\n" +
            "\tMSFT\t265\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t58\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t11\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t33\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t250\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t56\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t10\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t"));
  }

  @Test
  public void testCreationAndExecutionOfStrategyOnExistingPortfolioWithoutGivingWeights() {
    StringBuffer out = new StringBuffer();
    String input = "1\ndefault\n2\ndefault\n2\n2\n1\ngoog\nmsft\namzn\nfb\ndone" +
            "\n100000\n100\n03/11/2019\n04/15/2019\n25\n3\n3\n1\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t21\t$1175.76\tMon Mar 11 16:00:00 EDT 2019\t" +
            "$100.0\t\n" +
            "\tMSFT\t221\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t145\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t20\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t208\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t142\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t13\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0"));
  }

  @Test
  public void testExportStrategy() {
    StringBuffer out = new StringBuffer();
    String outputPath = "res/Strategy JSON's/";
    String input = "3\n1\ndefault\ngoog 40\nmsft 30\namzn 20\nfb 10\ndone\n100000\n100\n" +
            "03/11/2019\n04/15/2019\n25\n2\n" + outputPath + "\n3\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("Export Successful"));
  }

  @Test
  public void testImportStrategyUserCreated() {
    StringBuffer out = new StringBuffer();
    String inputPath = "res/Strategy JSON's/YO.json";
    String input = "5\n" + inputPath + "\ndefault\n2\ndefault\n1\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t2\t$1175.76\tMon Mar 11 16:00:00 EDT 2019\t" +
            "$100.0\t\n" +
            "\tMSFT\t22\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t14\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t1\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t2\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t20\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t14\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t1\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t"));
  }

  @Test
  public void testImportStrategyExportedFromApplication() {
    StringBuffer out = new StringBuffer();
    String inputPath = "res/Strategy JSON's/Strategy_20190417130111.json";
    String input = "5\n" + inputPath + "\ndefault\n2\ndefault\n1\n7\n6\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model, view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t21\t$1175.76\tMon Mar 11 16:00:00 EDT 2019\t" +
            "$100.0\t\n" +
            "\tMSFT\t221\t$112.83\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t145\t$172.07\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t14\t$1670.62\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t20\t$1207.15\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t208\t$119.89\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t142\t$175.72\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t13\t$1837.28\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t"));
  }
}