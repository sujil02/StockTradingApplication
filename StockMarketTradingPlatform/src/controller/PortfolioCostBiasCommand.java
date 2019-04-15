package controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import view.IView;

/**
 * This class contains implementation to execute the procedure which calculate the total cost bias
 * of this selectd {@link model.IPortfolio} linked to this user.
 */
public class PortfolioCostBiasCommand implements IPortfolioCommand {
  @Override
  public void execute(TextController textController, IView view, String portfolioName)
          throws IOException, NoSuchElementException {
    view.append("Enter Date on which Cost Bias will be calculated in MM/dd/yyyy format " +
            "Ex: 03/11/2019 for 11th March 2019");
    Date ref = getDateFromUser(view);
    float costBias = textController.getPortfolioCostBias(portfolioName, ref);
    String formatted = new DecimalFormat("#.##").format(costBias);
    view.append("Cost Bias:$" + formatted);
  }

  private Date getDateFromUser(IView view) throws IOException {
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
    return ref;
  }

  @Override
  public String gerDescription() {
    return "Get Cost Bias of this Portfolio";
  }
}
