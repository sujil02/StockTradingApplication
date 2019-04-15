package model;

import java.util.Date;
import java.util.List;

/**
 * This acts like a model to the application treating each identity as an individual user. Where
 * each user can hold a list of {@link IPortfolio} and manage them.
 */
public interface IUser {

  /**
   * This function call is responsible to create a new portfolio for this user.
   *
   * @param portfolioName Name of the portfolio which is to be created.
   * @throws IllegalArgumentException If the portfolio with the similar name already exist or input
   *                                  is empty or null.
   */
  void createPortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * Gets all the names of portfolio that are maintained for this user.
   *
   * @return List of names of all the portfolios maintained for the user.
   */
  List<String> getAllPortfolioNames();

  /**
   * This method trades {@link IStock} on a given portfolio. while buying if the {@link IStock}
   * already exists it adds new properties to the {@link IStock} retaining the previous properties.
   * If the {@link IStock} is now to {@link IPortfolio} it adds the stock to {@link IPortfolio} with
   * its properties
   *
   * @param portfolioName portfolio on which trades will happen
   * @param tradeType     type of trade being performed buying or selling
   * @param date          buying date of the stock
   * @param tickerSymbol  ticker symbol of company.
   * @param companyName   Company's Name
   * @param quantity      number of shares added
   * @throws IllegalArgumentException method throws this exception in following conditions 1. ticker
   *                                  symbol is null or empty 2. quantity is negative or 0 3.
   *                                  costPrice is negative or zero 4. purchaseDate is null
   */
  void makeATrade(String portfolioName, TradeType tradeType, Date date, String tickerSymbol,
                  String companyName, int quantity);

  /**
   * This method provides the contents of the given portfolio in string format.
   *
   * @param portfolioName name of portfolio to get contents
   * @return The output string is in following format:
   *         [companyName]\t[tickerSymbol]\t[quantity]\t[costPrice]\t[purchaseDate]
   *         If the {@link IStock} is bought more than once on a different date/time
   *         it will be represented as new entry in output.
   *         If the portfolio is empty an empty string is returned
   */
  IPortfolioV2 getPortfolioContents(String portfolioName);

  /**
   * This method calculates the value of given portfolio on a given date.
   *
   * @param portfolioName name of portfolio to calculate cost bias
   * @param refDate       date on which value of portfolio needs to be calculated.
   * @return value of portfolio on a given date.
   */
  float getPortfolioCostBias(String portfolioName, Date refDate);

  /**
   * This method calculates the value of the given portfolio on a given date.
   *
   * @param portfolioName name of portfolio to calculate value
   * @param refDate       date on which value of portfolio needs to be calculated.
   * @return value of portfolio on a given date.
   */
  float getPortfolioValue(String portfolioName, Date refDate);
}
