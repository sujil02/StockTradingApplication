package controller.textcommands;

import java.io.IOException;
import java.util.NoSuchElementException;

import controller.TextController;
import view.IView;

public class BuyStockCommand implements IPortfolioCommand {
  @Override
  public void execute(TextController textController, IView view, String portfolioName) throws IOException, NoSuchElementException {

  }

  @Override
  public String gerDescription() {
    return "Buy Shares on this portfolio";
  }
}
