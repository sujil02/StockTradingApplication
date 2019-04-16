package controller.Stratergy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controller.IFeatures;
import model.TradeType;
import view.IMainView;

public class DollarCostStrategy implements IStrategy {
  private String portfolioName;
  private TradeType tradeType;
  private Map<String, Float> tickerSymbols;
  private int totalQuantity;
  private float commission;
  private float investmentAmount;
  private Date startDate;
  private Date endDate;
  private int period;

  private DollarCostStrategy(String portfolioName, TradeType tradeType,
                             Map<String, Float> tickerSymbols, int totalQuantity,
                             float commission, float investmentAmount,
                             Date startDate, Date endDate,
                             int period) {
    this.portfolioName = portfolioName;
    this.tradeType = tradeType;
    this.tickerSymbols = tickerSymbols;
    this.totalQuantity = totalQuantity;
    this.commission = commission;
    this.investmentAmount = investmentAmount;
    this.startDate = startDate;
    this.endDate = endDate;
    this.period = period;

  }

  @Override
  public void buyStock(IFeatures controller, IMainView view) throws IOException {
    List<Date> purchaseDates = getPurchaseDates(startDate, endDate, period);
    for (Date purchaseDate : purchaseDates) {
      for (String stock : tickerSymbols.keySet()) {
        float weight = tickerSymbols.get(stock);
        if (totalQuantity != 0) {
          int quant = (int) weight * totalQuantity;
          while (purchaseDate.before(endDate) || purchaseDate.equals(endDate)) {
            try {
              controller.buyStocks(portfolioName, tradeType, purchaseDate
                      , stock, "", quant, commission);
              break;
            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Invalid Date")) {
                purchaseDate = addDay(purchaseDate);
              } else {
                throw new IllegalArgumentException("Invalid data");
              }
            }
          }

        } else {
          float amount = weight * investmentAmount;
          while (purchaseDate.before(endDate) || purchaseDate.equals(endDate)) {
            try {
              controller.buyStocks(portfolioName, tradeType, purchaseDate
                      , stock, "", amount, commission);
              break;
            } catch (IllegalArgumentException e) {
              if (e.getMessage().equals("Invalid Date") || e.getMessage().equals("Invalid Purchase Date")) {
                purchaseDate = addDay(purchaseDate);
              } else {
                throw new IllegalArgumentException("Invalid data");
              }
            }
          }
        }

      }
    }
    view.showSuccessMessage("Strategy completed");
  }

  @Override
  public void export() {

  }

  @Override
  public void setPortfolioName(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  public static IDollarCostStrategyBuilder getStrategyBuilder() {
    return new DollarCostWithPortfolioStrategyBuilder();
  }

  private List<Date> getPurchaseDates(Date startDate, Date endDate, int period) {
    List<Date> purchaseDates = new ArrayList<>();
    Date curr = startDate;
    Calendar c = Calendar.getInstance();
    while (curr.before(endDate) || curr.equals(endDate)) {
      purchaseDates.add(curr);
      c.setTime(curr);
      c.add(Calendar.DATE, period);
      curr = c.getTime();
    }
    return purchaseDates;
  }

  private Date addDay(Date refDate) {
    Date curr = refDate;

    Calendar c = Calendar.getInstance();
    c.setTime(curr);
    c.add(Calendar.DATE, 1);
    curr = c.getTime();

    return curr;
  }

  public static class DollarCostWithPortfolioStrategyBuilder extends DollarCostStrategyBuilder {


    @Override
    public IStrategy build() {
      return new DollarCostStrategy(this.portfolioName, this.tradeType,
              this.tickerSymbols, this.totalQuantity, this.commission, this.investmentAmount,
              this.startDate, this.endDate, this.period);
    }
  }

}
