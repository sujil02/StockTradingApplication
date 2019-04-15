package controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import view.IView;

/**
 * This class supports all the possible command that this application could perform over a {@link
 * model.IPortfolio}.
 */
public class ExaminePortfolioCommand implements ICommand {
  private Map<Integer, IPortfolioCommand> commandsMap;

  /**
   * Void constructor to initialize the list of all possible commands that could be executed over a
   * portfolio.
   */
  protected ExaminePortfolioCommand() {
    this.commandsMap = new LinkedHashMap<>();
    commandsMap.put(1, new PortfolioContentsCommand());
    commandsMap.put(2, new BuyStockCommand());
    commandsMap.put(3, new PortfolioCostBiasCommand());
    commandsMap.put(4, new PortfolioValueCommand());
    commandsMap.put(5, new ExportPortfolioCommand());
  }

  @Override
  public void execute(TextController textController, IView view)
          throws IOException, NoSuchElementException {
    String portfolioName = getPortfolioName(textController, view);

    while (true) {
      view.append("Selected Portfolio: " + portfolioName);
      for (int i : commandsMap.keySet()) {
        view.append(i + "\t" + commandsMap.get(i).gerDescription());
      }
      view.append(commandsMap.size() + 1 + "\tSelect new portfolio");
      view.append(commandsMap.size() + 2 + "\tBack");
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
        portfolioName = getPortfolioName(textController, view);
        continue;
      }
      if (option == commandsMap.size() + 2) {
        break;
      }
      commandsMap.get(option).execute(textController, view, portfolioName);
    }

  }

  @Override
  public String gerDescription() {
    return "Manage Portfolios";
  }

  private String getPortfolioName(TextController textController, IView view)
          throws IOException, NoSuchElementException {
    ICommand displayPortfolios = new DisplayPortfoliosCommand();
    displayPortfolios.execute(textController, view);
    view.append("Select Portfolio (Enter Portfolio Name)");
    String portfolioName;
    while (true) {
      portfolioName = view.getInput();
      if (textController.validatePortfolioName(portfolioName)) {
        break;
      }
      view.append("Invalid Portfolio Name. Try again");
    }
    return portfolioName;
  }
}
