package Stratergy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controller.IFeatures;
import model.TradeType;
import view.IMainView;

public class DollarCostWithPortfolioStrategy implements IStrategy {
  private String portfolioName;
  private TradeType tradeType;
  private List<String> tickerSymbols;
  private int totalQuantity;
  private float commission;
  private float investmentAmount;
  private List<Float> proportion;
  private Date startDate;
  private Date endDate;
  private int period;

  private DollarCostWithPortfolioStrategy(String portfolioName, TradeType tradeType,
                                          List<String> tickerSymbols, int totalQuantity,
                                          float commission, float investmentAmount,
                                          List<Float> proportion, Date startDate, Date endDate,
                                          int period) {
    this.portfolioName = portfolioName;
    this.tradeType = tradeType;
    this.tickerSymbols = tickerSymbols;
    this.totalQuantity = totalQuantity;
    this.commission = commission;
    this.investmentAmount = investmentAmount;
    this.proportion = proportion;
    this.startDate = startDate;
    this.endDate = endDate;
    this.period = period;

  }

  @Override
  public void buyStock(IFeatures controller, IMainView view) throws IOException {
    List<Date> purchaseDates = getPurchaseDates(startDate, endDate, period);
    for (Date purchaseDate : purchaseDates) {
      for (String stock : tickerSymbols) {
        float weight = proportion.get(tickerSymbols.indexOf(stock));
        if (totalQuantity != 0) {
          int quant = (int) weight * totalQuantity;
          while (purchaseDate.before(endDate)) {
            try {
              controller.buyStocks(portfolioName, tradeType, purchaseDate
                      , stock, "", quant, commission);
              break;
            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Invalid Date")) {
                purchaseDate = addDay(purchaseDate);
              }
            }
          }

        } else {
          float amount = weight * investmentAmount;
          while (purchaseDate.before(endDate)) {
            try {
              controller.buyStocks(portfolioName, tradeType, purchaseDate
                      , stock, "", amount, commission);
              break;
            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Invalid Date")) {
                purchaseDate = addDay(purchaseDate);
              }
            }
          }
        }

      }
    }
    view.showSuccessMessage("Strategy completed");
  }

  public static IDollarCostStrategyBuilder getStratergyBuilder() {
    return new DollarCostWithPortfolioStrategyBuilder();
  }

  private List<Date> getPurchaseDates(Date startDate, Date endDate, int period) {
    List<Date> purchaseDates = new ArrayList<>();
    Date curr = startDate;
    while (curr.before(endDate) || curr.equals(endDate)) {
      purchaseDates.add(curr);
      Calendar c = Calendar.getInstance();
      c.setTime(curr);
      c.add(Calendar.DATE, period);
      curr = c.getTime();
    }
    return purchaseDates;
  }

  private Date addDay(Date refDate) {
    Date curr = startDate;

    Calendar c = Calendar.getInstance();
    c.setTime(curr);
    c.add(Calendar.DATE, 1);
    curr = c.getTime();

    return curr;
  }

  private static class DollarCostWithPortfolioStrategyBuilder extends DollarCostStrategyBuilder {


    @Override
    public IStrategy build() {
      return new DollarCostWithPortfolioStrategy(this.portfolioName, this.tradeType,
              this.tickerSymbols, this.totalQuantity, this.commission, this.investmentAmount,
              this.proportion, this.startDate, this.endDate, this.period);
    }
  }

}
