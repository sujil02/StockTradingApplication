package controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import model.IPortfolioV2;
import model.IStock;
import view.IView;

/**
 * A class that contains implementation to execute the procedure to get all the
 * {@link model.IPortfolio} linked to this user.
 */
public class PortfolioContentsCommand implements IPortfolioCommand {
  @Override
  public void execute(TextController textController, IView view, String portfolioName)
          throws IOException, NoSuchElementException {
    try {
      IPortfolioV2 portfolio = textController.getPortfolioContents(portfolioName);
      StringBuilder contents = new StringBuilder();
      for (IStock s : portfolio.getOwnedStocks()) {
        contents.append(s.getCompanyName() + "\t");
        contents.append(s.getTickerSymbol() + "\t");
        contents.append(s.getQuantity() + "\t");
        contents.append("$" + s.getCostPrice() + "\t");
        contents.append(s.getPurchaseDate() + "\t");
        contents.append("$" + s.getCommission() + "\t");
        contents.append("\n");
      }
      view.append(contents.toString());
    } catch (IllegalArgumentException e) {
      view.append("Invalid portfolio name given. " + e.getMessage());
    }
  }

  @Override
  public String gerDescription() {
    return "Display contents of this portfolio";
  }
}
