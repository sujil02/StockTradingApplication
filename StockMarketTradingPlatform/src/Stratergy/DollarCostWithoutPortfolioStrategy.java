package Stratergy;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import controller.IFeatures;
import model.TradeType;
import view.IMainView;

public class DollarCostWithoutPortfolioStrategy implements IStrategy {
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

  private DollarCostWithoutPortfolioStrategy(String portfolioName, TradeType tradeType,
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
    try {
      controller.createPortfolio(portfolioName);
      IStrategy buyingStrategy = DollarCostWithPortfolioStrategy
              .getStratergyBuilder().setPortfolioName(portfolioName)
              .setTradeType(tradeType).setTickerSymbolsAndProportions(tickerSymbols, proportion)
              .setTotalQuantity(totalQuantity).setInvestmentAmount(investmentAmount)
              .setDuration(startDate, endDate, period).build();
      buyingStrategy.buyStock(controller, view);
    } catch (IllegalArgumentException e) {
      view.showErrorMessage("Error creating strategy" + e.getMessage());
    }
  }

  public static IDollarCostStrategyBuilder getStratergyBuilder() {
    return new DollarCostWithoutPortfolioStrategyBuilder();
  }

  private static class DollarCostWithoutPortfolioStrategyBuilder extends DollarCostStrategyBuilder {

    @Override
    public IStrategy build() {
      return new DollarCostWithoutPortfolioStrategy(this.portfolioName, this.tradeType,
              this.tickerSymbols, this.totalQuantity, this.commission, this.investmentAmount,
              this.proportion, this.startDate, this.endDate, this.period);
    }
  }
}
