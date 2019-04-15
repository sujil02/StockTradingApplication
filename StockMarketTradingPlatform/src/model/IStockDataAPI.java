package model;

import java.util.Date;
import java.util.Map;

/**
 * This interface is used to get data for the given company(using its ticker symbol). The interface
 * can be implemented using any API which can provide the data.
 */
public interface IStockDataAPI {

  /**
   * This method gets the stock data for the given company's. It returns all the attributes returned
   * from the API in a key value pair, where attributes will be the key and the value returned from
   * API in string format.
   *
   * @param tickerSymbol ticker symbol of the company
   * @param refDate      date on which the data is required
   * @return Map which contains the attributes returned from the API as the key and data returned
   *         from the API as value.
   * @throws IllegalArgumentException If there is no data found on the given date on API
   */
  Map<String, String> getStockData(String tickerSymbol, Date refDate)
          throws IllegalArgumentException;
}
