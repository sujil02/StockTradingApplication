package model;

import java.util.Date;
import java.util.List;

/**
 * This is the new version of the portfolio supporting additional functionalities. like export
 * import trade with commissions.
 */
public interface IPortfolioV2 extends IPortfolio {
  /**
   * This method trades {@link IStock} on a  portfolio. while buying if the {@link IStock} already
   * exists it adds new properties to the {@link IStock} retaining the previous properties.
   *
   * @param tradeType    type of trade being performed buying or selling
   * @param date         buying date of the stock
   * @param tickerSymbol ticker symbol of company.
   * @param companyName  Company's Name
   * @param quantity     number of shares added
   * @param commission   commission paid on a trade
   * @throws IllegalArgumentException method throws this exception in following conditions 1. ticker
   *                                  symbol is null or empty 2. quantity is negative or 0 3.
   *                                  costPrice is negative or zero 4. purchaseDate is null
   */
  void makeATrade(TradeType tradeType, Date date, String tickerSymbol,
                  String companyName, int quantity, float commission);

  /**
   * This method trades {@link IStock} on a  portfolio. while buying if the {@link IStock} already
   * exists it adds new properties to the {@link IStock} retaining the previous properties.
   *
   * @param tradeType        type of trade being performed buying or selling
   * @param date             buying date of the stock
   * @param tickerSymbol     ticker symbol of company.
   * @param companyName      Company's Name
   * @param investmentAmount Anount for which the share is to be purchased.
   * @param commission       commission paid on a trade
   * @throws IllegalArgumentException method throws this exception in following conditions 1. ticker
   *                                  symbol is null or empty 2. quantity is negative or 0 3.
   *                                  costPrice is negative or zero 4. purchaseDate is null
   */
  void makeATrade(TradeType tradeType, Date date, String tickerSymbol,
                  String companyName, float investmentAmount, float commission);

  /**
   * Exports the portfolio contents as a JSON file.
   *
   * @param path Path where the portfolio should be exported.
   * @throws IllegalArgumentException If given portfolio name is not found.
   */
  void exportPortfolio(String path) throws IllegalArgumentException;

  /**
   * Returns all the stocks current holding in the portfolio.
   *
   * @return List of stock objects.
   */
  List<IStock> getOwnedStocks();


}
