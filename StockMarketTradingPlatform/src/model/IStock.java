package model;

import java.util.Date;

/**
 * This represents a company stock in a particular stock market. It is defined by its company name
 * and ticker symbol. ticker symbol are unique to a company.
 */
public interface IStock {
  /**
   * This method provides the company's name.
   *
   * @return company's name. It can provide empty string if company name is not provided.
   */
  String getCompanyName();

  /**
   * This method provides the company's ticker symbol.
   *
   * @return company's ticker symbol.
   */
  String getTickerSymbol();

  /**
   * this method provides the value of a stock by taking average of its highest and lowest price on
   * the given date.
   *
   * @param refDate date on which the stock's is required.
   * @return value of the stock by taking average of highest and lowest price on a given date
   * @throws IllegalArgumentException If the stock details is not present on the given date.
   */
  float getValue(Date refDate) throws IllegalArgumentException;

  /**
   * This method processes the numbers of shares of this type present in the portfolio.
   *
   * @return the number of shares present of this type.
   */
  int getQuantity();

  float getCostPrice();

  float getCostBias();

  Date getPurchaseDate();

  float getCommission();

  String getAttributes();

  void setCostPrice();

  void setQuantity(float investmentAmount);

  IStock copy();


}
