package controller.textcommands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import controller.Stratergy.DollarCostStrategy;
import controller.Stratergy.IStrategy;
import controller.TextController;
import model.TradeType;
import view.IView;

public class BuyUsingDollarCostStrategyCommand implements ICommand, IPortfolioCommand {
  private Map<Integer, String> commandsMap;

  public BuyUsingDollarCostStrategyCommand() {
    commandsMap = new HashMap<>();
    commandsMap.put(1, "Create Strategy");
    commandsMap.put(2, "Export Strategy");
  }

  @Override
  public void execute(TextController textController, IView view) throws IOException, NoSuchElementException {
    while (true) {
      int option = getInput(view);
      if (option == commandsMap.size() + 1) {
        break;
      }
      switch (option) {
        case 1: {
          createStrategy(textController, view);
          break;
        }
        case 2: {
          //TODO add export strategy
          break;
        }
        default: {
          view.append("Invalid Command Try Again");
        }
      }
    }


  }

  @Override
  public void execute(TextController textController, IView view, String portfolioName)
          throws IOException, NoSuchElementException {
    while (true) {
      int option = getInput(view);
      if (option == commandsMap.size() + 1) {
        break;
      }
      switch (option) {
        case 1: {
          createStrategy(textController, view, portfolioName);
          break;
        }
        case 2: {
          //TODO add export strategy
          break;
        }
        default: {
          view.append("Invalid Command Try Again");
        }
      }
    }
  }

  @Override
  public String gerDescription() {
    return "Buy Stocks using Dollar Cost Averaging Strategy";
  }

  private void createStrategy(TextController textController, IView view) throws IOException {
    try {
      view.append("Enter new Portfolio Name");
      String portfolioName = view.getInput();
      textController.createPortfolio(portfolioName);
      createStrategy(textController, view, portfolioName);

    } catch (IllegalArgumentException e) {
      view.append("Error creating strategy" + e.getMessage());
    }
  }

  private void createStrategy(TextController textController, IView view, String portfolioName)
          throws IOException {
    view.append("Enter Ticker Symbol with its weight in percentage (if weights are equal add only " +
            "ticker symbol) one on each line. Enter \"Done\" to finalize the list");
    Map<String, Float> tickerSymbols = getTickerSymbolsFromUser(view);
    if (tickerSymbols.values().stream().mapToDouble(x -> x.doubleValue()).sum() == 0) {
      float weight = (float) 1 / tickerSymbols.size();
      for (String tickerSymbol : tickerSymbols.keySet()) {
        tickerSymbols.put(tickerSymbol, weight);
      }
    }
    if (Math.abs(tickerSymbols.values().stream().mapToDouble(x -> x.doubleValue()).sum() - 1) > 0.01) {
      view.append("Weighs Does Not equal to 100%");
      return;
    }

    view.append("Enter amount to be invested(enter 0 if wish to invest using quantity)");
    float investment = getFloatFromUser(view);

    int quant = 0;
    if (investment == 0) {
      view.append("Enter quantity of stock bought. (Enter 0 to invest using amount )");
      quant = getIntFromUser(view);
    }
    view.append("Enter Commission");
    float commission = getFloatFromUser(view);

    view.append("Enter Start Date(optional)");
    Date startDate = addTimeToDate(getDateFromUser(view));

    view.append("Enter End Date(optional)");
    Date endDate = addTimeToDate(getDateFromUser(view));

    view.append("Enter frequency of purchase in days(enter 0 if no repetition)");
    int freq = getIntFromUser(view);
    freq = (freq == 0) ? 1 : freq;

    try {
      textController.setStrategy(DollarCostStrategy.getStrategyBuilder().setPortfolioName(portfolioName)
              .setTickerSymbolsAndProportions(tickerSymbols)
              .setInvestmentAmount(investment).setTotalQuantity(quant).setTradeType(TradeType.BUY)
              .setDuration(startDate, endDate, freq).setCommission(commission).build());
      textController.executeStrategy(textController);
    } catch (IllegalArgumentException e) {
      view.append("Error Creating strategy try again");
    }
  }

  private int getInput(IView view) throws IOException {
    for (int i : commandsMap.keySet()) {
      view.append(i + "\t" + commandsMap.get(i));
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
    return option;
  }

  private Date getDateFromUser(IView view) throws IOException {
    Date ref;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    df.setLenient(false);
    while (true) {
      try {
        String refDate = view.getInput();
        if (refDate.isEmpty()) {
          return df.parse(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        }
        ref = df.parse(refDate);
        break;
      } catch (ParseException e) {
        view.append("Invalid Date. Try Again.");
      }
    }
    return ref;
  }

  private Map<String, Float> getTickerSymbolsFromUser(IView view) throws IOException {
    Map<String, Float> tickerSymbols = new HashMap<>();
    String input = view.getInput();
    while (!input.toUpperCase().equals("DONE")) {
      String[] inputs = input.split("\\s+");
      if (inputs.length > 1) {
        String tickerSymbol = inputs[0];
        float weight;
        try {
          weight = Float.parseFloat(inputs[1]) / 100;
        } catch (NumberFormatException e) {
          view.append("Invalid Data. Try Again.");
          continue;
        }
        tickerSymbols.put(tickerSymbol, weight);
      } else {
        String tickerSymbol = inputs[0];
        float weight = 0;
        tickerSymbols.put(tickerSymbol, weight);
      }
      input = view.getInput();
    }
    return tickerSymbols;
  }

  private float getFloatFromUser(IView view) throws IOException {
    float num;
    while (true) {
      try {
        num = Float.parseFloat(view.getInput());
        break;
      } catch (NumberFormatException e) {
        view.append("Invalid data. Try Again.");
      }
    }
    return num;
  }

  private int getIntFromUser(IView view) throws IOException {
    int num;
    while (true) {
      try {
        num = Integer.parseInt(view.getInput());
        break;
      } catch (NumberFormatException e) {
        view.append("Invalid data. Try Again.");
      }
    }
    return num;
  }

  private Date addTimeToDate(Date ref) {
    Calendar c = Calendar.getInstance();
    c.setTime(ref);
    c.set(Calendar.HOUR, 16);
    c.set(Calendar.MINUTE, 00);
    c.set(Calendar.SECOND, 00);
    return c.getTime();
  }
}

