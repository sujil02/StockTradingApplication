package controller.textcommands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import controller.TextController;
import view.IView;

/**
 * Class handles the command to buy a stock for a particular user over the selected portfolio by the
 * user.
 */
public class BuyStockCommand implements IPortfolioCommand {
  private Map<Integer, IPortfolioCommand> commandsMap;

  /**
   * Void constructor to initialize the list of all possible commands that could be executed over a
   * portfolio.
   */
  public BuyStockCommand() {
    commandsMap = new HashMap<>();
    commandsMap.put(1, new BuyStockUsingDefaultStrategyCommand());
    commandsMap.put(2, new BuyUsingDollarCostStrategyCommand());
  }

  @Override
  public void execute(TextController textController, IView view, String portfolioName)
          throws IOException, NoSuchElementException {
    while (true) {
      view.append("Selected Portfolio: " + portfolioName);
      for (int i : commandsMap.keySet()) {
        view.append(i + "\t" + commandsMap.get(i).gerDescription());
      }
      view.append(commandsMap.size() + 1 + "\tBack");
      view.append("Select option");
      int option;
      while (true) {
        try {
          String quantity = view.getInput();
          option = Integer.parseInt(quantity);
          break;
        } catch (NumberFormatException e) {
          view.append("Invalid option. Try Again.");
        }
      }
      if (option == commandsMap.size() + 1) {
        break;
      }
      commandsMap.get(option).execute(textController, view, portfolioName);
    }
  }

  @Override
  public String gerDescription() {
    return "Buy Shares on this portfolio";
  }
}
