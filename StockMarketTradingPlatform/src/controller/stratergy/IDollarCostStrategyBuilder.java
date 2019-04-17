package controller.stratergy;

import java.util.Date;
import java.util.Map;

import model.TradeType;

/**
 * This interface is used as a builder class implementation to make a strategy object ensuring all
 * the neccessary parameters are included in the strategy before its executed.
 */

public interface IDollarCostStrategyBuilder {
  /**
   * Sets the portfolio name for strategy.
   */
  IDollarCostStrategyBuilder setPortfolioName(String portfolioName);

  /**
   * Sets TradeType of strategy.
   */
  IDollarCostStrategyBuilder setTradeType(TradeType tradeType);

  /**
   * Sets the ticker symbols involved in the trade along with its weights according to which the
   * investment amount will be divided.
   *
   * @param tickerSymbols map of ticker symbols and thier corresponding weights.
   */
  IDollarCostStrategyBuilder setTickerSymbols(Map<String, Float> tickerSymbols);

  /**
   * Sets the total quantity of the stocks bought on each day of transaction. if quantity is
   * greater than zero it will reset the investment amount to 0.
   */
  IDollarCostStrategyBuilder setTotalQuantity(int totalQuantity);

  /**
   * Sets the commission paid on each trade.
   */
  IDollarCostStrategyBuilder setCommission(float commission);

  /**
   * Sets the total amount to be invested on each day of transaction. If the amount is greater
   * than 0 method will set quantity to zero.
   */
  IDollarCostStrategyBuilder setInvestmentAmount(float investmentAmount);

  /**
   * Sets the duration of the strategy.
   *
   * @param startDate start date of the strategy
   * @param endDate   end date of strategy.
   * @param period    frequency of transaction during the duration of strategy in days.
   */
  IDollarCostStrategyBuilder setDuration(Date startDate, Date endDate, int period);

  /**
   * creates the strategy object based on the parameters provided.
   *
   * @return DollarCostStrategy object
   */

  IStrategy build();

}
