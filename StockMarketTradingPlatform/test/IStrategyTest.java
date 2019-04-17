import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.IController;
import controller.TextController;
import model.IUserV2;
import model.UserImpl;
import view.IView;
import view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IStrategyTest {

  @Test
  public void testCreationAndExecutionOfStrategyOnNewPortfolio(){
    StringBuffer out = new StringBuffer();
    String input = "3\n1\ndefault\ngoog 40\nmsft 30\namzn 20\nfb 10\ndone\n100000\n100\n" +
            "03/11/2019\n04/15/2019\n25\n3\n2\ndefault\n1\n7\n5\n";
    Reader in = new StringReader(input);
    IView view = new View(in, out);
    IUserV2 model = new UserImpl();
    IController controller = new TextController(model,view);
    controller.start();
    assertTrue(out.toString().contains("GOOG\t34\t$1160.32\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t267\t$111.965\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t57\t$172.94\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t12\t$1649.15\tMon Mar 11 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tGOOG\t33\t$1210.625\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tMSFT\t250\t$119.8\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tFB\t56\t$176.05\tFri Apr 05 16:00:00 EDT 2019\t$100.0\t\n" +
            "\tAMZN\t10\t$1831.885\tFri Apr 05 16:00:00 EDT 2019\t$100.0"));
  }

}