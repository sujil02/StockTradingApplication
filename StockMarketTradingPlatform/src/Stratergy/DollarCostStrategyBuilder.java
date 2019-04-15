package Stratergy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.TradeType;

abstract class DollarCostStrategyBuilder implements IDollarCostStrategyBuilder {
  protected String portfolioName;
  protected TradeType tradeType;
  protected List<String> tickerSymbols;
  protected int totalQuantity;
  protected float commission;
  protected float investmentAmount;
  protected List<Float> proportion;
  protected Date startDate;
  protected Date endDate;
  protected int period;

  protected DollarCostStrategyBuilder() {
    this.portfolioName = "";
    this.tradeType = TradeType.BUY;
    this.tickerSymbols = new ArrayList<>();
    this.totalQuantity = 0;
    this.commission = 0;
    this.investmentAmount = 0;
    this.proportion = new ArrayList<>();
    this.startDate = new Date();
    this.endDate = new Date();
    this.period = 0;
  }

  public IDollarCostStrategyBuilder setPortfolioName(String portfolioName) {
    this.portfolioName = portfolioName;
    return this;
  }

  public IDollarCostStrategyBuilder setTradeType(TradeType tradeType) {
    this.tradeType = tradeType;
    return this;
  }

  public IDollarCostStrategyBuilder setTickerSymbolsAndProportions(List<String> tickerSymbols, List<Float> proportion) {
    if (tickerSymbols.size() != proportion.size()) {
      throw new IllegalArgumentException("Invalid Input");
    }
    if (proportion.stream().mapToDouble(x -> x.doubleValue()).sum() != 1) {
      throw new IllegalArgumentException("Invalid Proportion");
    }
    this.tickerSymbols = tickerSymbols;
    this.proportion = proportion;
    return this;
  }

  public IDollarCostStrategyBuilder setTotalQuantity(int totalQuantity) {
    if (totalQuantity > 0) {
      this.totalQuantity = totalQuantity;
      this.investmentAmount = 0;
    } else {
      this.totalQuantity = totalQuantity;
    }
    return this;
  }

  public IDollarCostStrategyBuilder setCommission(float commission) {
    this.commission = commission;
    return this;
  }

  public IDollarCostStrategyBuilder setInvestmentAmount(float investmentAmount) {
    if (investmentAmount > 0) {
      this.investmentAmount = investmentAmount;
      this.totalQuantity = 0;
    } else {
      this.investmentAmount = investmentAmount;
    }
    return this;
  }

  public IDollarCostStrategyBuilder setDuration(Date startDate, Date endDate, int period) {
    if (endDate.before(startDate)) {
      throw new IllegalArgumentException("End date can not be before start date");
    }
    if (period < 0) {
      throw new IllegalArgumentException("Duration can not be negative");
    }
    this.startDate = startDate;
    this.endDate = endDate;
    this.period = period;
    return this;
  }
}