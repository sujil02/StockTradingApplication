package model;

import java.util.Date;

/**
 * Portfolio is a collection of stocks. This class represents a portfolio of {@link IStock}. It
 * provides features to view portfolio contents, cost bias and current value. It also allows user to
 * add more stocks to portfolio.
 */
public interface IPortfolio {

  /**
   * This method calculates the cost bias (i.e. cost price of entire portfolio) of the portfolio on
   * a given date.
   *
   * @param refDate date on which cost bias needs to be calculated.
   * @return cost bias of portfolio.
   * @throws IllegalArgumentException if refDate is null
   */
  float getPortfolioCostBias(Date refDate) throws IllegalArgumentException;

  /**
   * This method calculates the value of portfolio on a given date.
   *
   * @param refDate date on which value of portfolio needs to be calculated.
   * @return value of portfolio on a given date.
   * @throws IllegalArgumentException if refDate is null
   */
  float getPortfolioValue(Date refDate) throws IllegalArgumentException;

  /**
   * method provides the contents of the portfolio in string format.
   *
   * @return The output string is in following format:
   *         [companyName]\t[tickerSymbol]\t[quantity]\t[costPrice]\t[purchaseDate]
   *         If the {@link IStock} is bought more than once on a different date/time
   *         it will be represented as new entry in output.
   *         If the portfolio is empty an empty string is returned
   */
  String getPortfolioContents();

  /**
   * Method provides unique name of portfolio.
   *
   * @return portfolio name
   */
  String getPortfolioName();

  /**
   * This method trades {@link IStock} on a  portfolio. while buying if the {@link IStock} already
   * exists it adds new properties to the {@link IStock} retaining the previous properties.
   *
   * @param tradeType    type of trade being performed buying or selling
   * @param date         buying date of the stock
   * @param tickerSymbol ticker symbol of company.
   * @param companyName  Company's Name
   * @param quantity     number of shares added
   * @throws IllegalArgumentException method throws this exception in following conditions 1. ticker
   *                                  symbol is null or empty 2. quantity is negative or 0 3.
   *                                  costPrice is negative or zero 4. purchaseDate is null
   */
  void makeATrade(TradeType tradeType, Date date, String tickerSymbol,
                  String companyName, int quantity);

}
