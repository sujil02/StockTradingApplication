package controller.textcommands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import controller.TextController;
import model.TradeType;
import view.IView;

/**
 * Class handles the command to buy a stock for a particular user over the selected portfolio by the
 * user.
 */
public class BuyStockUsingDefaultStrategyCommand implements IPortfolioCommand {
  @Override
  public void execute(TextController textController, IView view, String portfolioName)
          throws IOException, NoSuchElementException {
    view.append("Enter Company Name(Optional)");
    String companyName = view.getInput();
    view.append("Enter Company Ticker Symbol(Mandatory)");
    String tickerSymbol = view.getInput();
    view.append("Enter quantity of stock bought. (Enter 0 to invest using amount )");
    int quant;
    while (true) {
      try {
        String quantity = view.getInput();
        quant = Integer.parseInt(quantity);
        break;
      } catch (NumberFormatException e) {
        view.append("Invalid data. Try Again.");
      }
    }
    float investment = 0;
    if (quant == 0) {
      view.append("Enter amount to be invested");
      while (true) {
        try {
          investment = Float.parseFloat(view.getInput());
          break;
        } catch (NumberFormatException e) {
          view.append("Invalid data. Try Again.");
        }
      }
    }
    view.append("Enter Date and Time of buying stock in MM/dd/yyyy HH:mm:ss (24 hour format) " +
            "Ex: 03/11/2019 12:00:00 for 11th March 2019 12:00 pm");
    Date ref = getDateInputFromUser(view);
    view.append("Enter Commission");
    float commission;
    while (true) {
      try {
        commission = Float.parseFloat(view.getInput());
        break;
      } catch (NumberFormatException e) {
        view.append("Invalid data. Try Again.");
      }
    }
    try {
      if (quant != 0) {
        textController.buyStocks(portfolioName, TradeType.BUY, ref, tickerSymbol
                , companyName, quant, commission);
      } else {
        textController.buyStocks(portfolioName, TradeType.BUY, ref, tickerSymbol
                , companyName, investment, commission);
      }
      view.append("Trade completed successfully.");
    } catch (IllegalArgumentException e) {
      view.append("Transaction could not be completed.\n" + e.getMessage());
    }


  }

  private Date getDateInputFromUser(IView view) {
    Date referenceDate;
    while (true) {
      try {
        String date = view.getInput();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        df.setLenient(false);
        referenceDate = df.parse(date);
        break;
      } catch (ParseException e) {
        try {
          view.append("Invalid Date. Try Again.");
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
    return referenceDate;
  }

  @Override
  public String gerDescription() {
    return "Buy Stocks using no Strategy on this portfolio";
  }
}