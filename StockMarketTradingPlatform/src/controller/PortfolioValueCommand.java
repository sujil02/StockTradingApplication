package controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import view.IView;

/**
 * A class that contains implementation to execute the procedure to calculate the total value of
 * this {@link model.IPortfolio} linked to this user.
 */

public class PortfolioValueCommand implements IPortfolioCommand {
  @Override
  public void execute(TextController textController, IView view, String portfolioName)
          throws IOException, NoSuchElementException {
    view.append("Enter Date on which Value will be calculated in MM/dd/yyyy format " +
            "Ex: 03/11/2019 for 11th March 2019");
    Date ref;
    while (true) {
      try {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setLenient(false);
        ref = df.parse(view.getInput());
        break;
      } catch (ParseException e) {
        view.append("Invalid Date. Try Again.");
      }
    }
    float costBias = textController.getPortfolioValue(portfolioName, ref);
    DecimalFormat df = new DecimalFormat("#.##");
    String formatted = df.format(costBias);
    view.append("Value:$" + formatted);
  }

  @Override
  public String gerDescription() {
    return "Get current value of this portfolio.";
  }
}
