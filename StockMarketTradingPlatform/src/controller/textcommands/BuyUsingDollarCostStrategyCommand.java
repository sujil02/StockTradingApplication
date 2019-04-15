package controller.textcommands;

import java.io.IOException;
import java.util.NoSuchElementException;

import controller.TextController;
import view.IView;

public class BuyUsingDollarCostStrategyCommand implements ICommand {
  @Override
  public void execute(TextController textController, IView view) throws IOException, NoSuchElementException {
    try {
      ICommand createPortfolio = new CreatePortfolioCommand();
      createPortfolio.execute(textController,view);

    } catch (IllegalArgumentException e) {
      view.append("Error creating strategy" + e.getMessage());
    }
  }

  @Override
  public String gerDescription() {
    return "Buy Stocks using Dollar Cost Averaging Strategy";
  }
}
