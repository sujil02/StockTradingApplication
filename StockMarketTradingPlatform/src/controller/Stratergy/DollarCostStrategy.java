package controller.Stratergy;

import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controller.IFeatures;
import model.TradeType;
import view.IMainView;

/**
 * This class implements {@link IStrategy} to implement Dollar Cost Average Strategy. This
 * strategy is based on the well-founded hypothesis that although the stock prices fluctuate every
 * day, the long-term trend of a well-chosen portfolio is upward. According to this strategy a
 * fixed amount is invested in fixed set of stocks for a particular duration recursively to earn
 * profit over the investment.
 */
public class DollarCostStrategy implements IStrategy {
  private String portfolioName;
  private TradeType tradeType;
  @Expose
  private Map<String, Float> tickerSymbols;
  @Expose
  private int totalQuantity;
  @Expose
  private float commission;
  @Expose
  private float investmentAmount;
  @Expose
  private Date startDate;
  @Expose
  private Date endDate;
  @Expose
  private int period;

  public DollarCostStrategy(String portfolioName, TradeType tradeType,
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

  /**
   * This method buys stock using dollar cost averaging strategy. All parameters for the strategy
   * are set using the {@link IDollarCostStrategyBuilder}. According to this strategy a fixed
   * amount is invested in fixed set of stocks for a particular duration recursively to earn
   * profit over the investment.
   *
   * While buying stocks for the provided duration if the trade day falls on a day when market is
   * closed then the trade will be completed on the next day when the market is open. If the end
   * date of the strategy is not provided in the parameters the trade days are calculated till the
   * day on which strategy is being executed. If the weights are not provided when providing
   * ticker symbols of the companies the weights are equally distributed. If the frequency of
   * trade is not mentioned it will be consider as 1 i.e. will be executed only once.
   *
   * @param controller controller from which this strategy is being called.
   * @param view       view being used when this strategy is called.
   */
  @Override
  public void buyStock(IFeatures controller, IMainView view) throws IOException {
    List<Date> purchaseDates = getPurchaseDates(startDate, endDate, period);
    validateWeightsInStategy();
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
                throw new IllegalArgumentException("Invalid data" + e.getMessage());
              }
            }
          }
        }

      }
    }
    view.showSuccessMessage("Strategy completed");
  }

  private void validateWeightsInStategy() {
    if (Math.abs(tickerSymbols.values().stream().mapToDouble(x -> x.doubleValue()).sum() - 1) > 0.01) {
      throw new IllegalArgumentException("Weighs Does Not equal to 100%");
    }
  }

  @Override
  public void setPortfolioName(String portfolioName) {
    this.portfolioName = portfolioName;
    this.tradeType = TradeType.BUY;
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
