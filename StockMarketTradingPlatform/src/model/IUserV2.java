package model;

import java.io.IOException;
import java.util.Date;

/**
 * Addition of new feature of Trades with commissions support to IUser
 * interface.
 */
public interface IUserV2 extends IUser {
  /**
   * This method trades {@link IStock} on a given portfolio. while buying if the
   * {@link IStock} already exists it adds new properties to the {@link IStock}
   * retaining the previous properties. If the {@link IStock} is now to {@link
   * IPortfolio} it adds the stock to {@link IPortfolio} with its properties
   *
   * @param portfolioName portfolio on which trades will happen
   * @param tradeType     type of trade being performed buying or selling
   * @param date          buying date of the stock
   * @param tickerSymbol  ticker symbol of company.
   * @param companyName   Company's Name
   * @param quantity      number of shares added
   * @param commission    commission paid for the trade.
   * @throws IllegalArgumentException method throws this exception in following
   *                                  conditions 1. ticker symbol is null or
   *                                  empty 2. quantity is negative or 0 3.
   *                                  costPrice is negative or zero 4.
   *                                  purchaseDate is null
   */
  void makeATrade(String portfolioName, TradeType tradeType, Date date, String tickerSymbol,
                  String companyName, int quantity, float commission);


  /**
   * This method trades {@link IStock} on a given portfolio. while buying if the
   * {@link IStock} already exists it adds new properties to the {@link IStock}
   * retaining the previous properties. If the {@link IStock} is now to {@link
   * IPortfolio} it adds the stock to {@link IPortfolio} with its properties.
   * While calculating quantity of stock to be bought using investment amount
   * the quantity is rounded to previous while number.
   *
   * @param portfolioName    portfolio on which trades will happen
   * @param tradeType        type of trade being performed buying or selling
   * @param date             buying date of the stock
   * @param tickerSymbol     ticker symbol of company.
   * @param companyName      Company's Name
   * @param investmentAmount number of shares added
   * @param commission       commission paid for the trade.
   * @throws IllegalArgumentException method throws this exception in following
   *                                  conditions 1. ticker symbol is null or
   *                                  empty 2. quantity is negative or 0 3.
   *                                  costPrice is negative or zero 4.
   *                                  purchaseDate is null
   */

  void makeATrade(String portfolioName, TradeType tradeType, Date date,
                  String tickerSymbol, String companyName,
                  float investmentAmount, float commission);

  /**
   * This method saves the portfolio data in a file at the provided location.
   * Name of the file is as portfolio name. It saves all the trades done by a
   * portfolio in a persistent memory. The file is created in following format:
   *{
   *   "portfolioName": "[Portfolio Name]",
   *   "ownedStocks": [
   *     {
   *       "companyName": "[Company Name]",
   *       "tickerSymbol": "[Company Ticket Symbol]",
   *       "quantity": [Number of stocks bought],
   *       "costPrice": [Cost of single stock],
   *       "purchaseDate": "[Date and Time in format MM/dd/YYYy HH:mm:ss] ",
   *       "commission": [Commission paid on trade]
   *     },
   *   ]
   * }
   *If more than one stocks are bought than there will be multiple entries in
   * ownedStocks block. Example:
   * {
   *   "portfolioName": "Default",
   *   "ownedStocks": [
   *     {
   *       "companyName": "Google",
   *       "tickerSymbol": "GOOG",
   *       "quantity": 100,
   *       "costPrice": 1189.59,
   *       "purchaseDate": "03/15/2019 12:00:00",
   *       "commission": 0.0
   *     },
   *     {
   *       "companyName": "Microsoft",
   *       "tickerSymbol": "MSFT",
   *       "quantity": 500,
   *       "costPrice": 111.965,
   *       "purchaseDate": "03/11/2019 12:00:00",
   *       "commission": 0.0
   *     }
   *     ]
   * }
   *
   * @param portfolioName Name of portfolio to be exported
   * @param path          location directory where portfolio needs to be
   *                      exported
   * @throws IOException If file cannot be created
   */
  void exportPortfolio(String portfolioName, String path) throws IOException;

  /**
   * This method reads the .json files created by user or exported from
   * application and ads the portfolio to the {@link IUserV2} model. User can
   * now perform all the operations which on imported portfolio which it can on
   * new portfolio.
   *
   * @param path path of the file needs to be imported.
   * @throws IOException              if file cannot be read
   * @throws IllegalArgumentException if there is an error in file format or
   *                                  portfolio with same name exist.
   */
  void importPortfolio(String path) throws IOException;

}
