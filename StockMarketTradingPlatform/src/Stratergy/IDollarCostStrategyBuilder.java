package Stratergy;

import java.util.Date;
import java.util.Map;

import model.TradeType;

public interface IDollarCostStrategyBuilder {
  IDollarCostStrategyBuilder setPortfolioName(String portfolioName);

  IDollarCostStrategyBuilder setTradeType(TradeType tradeType);

  IDollarCostStrategyBuilder setTickerSymbolsAndProportions(Map<String, Float> tickerSymbols);

  IDollarCostStrategyBuilder setTotalQuantity(int totalQuantity);

  IDollarCostStrategyBuilder setCommission(float commission);

  IDollarCostStrategyBuilder setInvestmentAmount(float investmentAmount);

  IDollarCostStrategyBuilder setDuration(Date startDate, Date endDate, int period);

  IStrategy build();

}
