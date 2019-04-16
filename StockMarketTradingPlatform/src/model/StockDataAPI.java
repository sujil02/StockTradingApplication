package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the IStockDataAPI using Alpha Vantage API to get the
 * data.
 */
public class StockDataAPI implements IStockDataAPI {

  /**
   * This method gets the stock data for the given company's. It returns all the
   * attributes returned from the API in a key value pair, where attributes will
   * be the key and the value returned from API in string format.
   *
   * @param tickerSymbol ticker symbol of the company
   * @param refDate      date on which the data is required
   * @return The Map returns all the attributes returned from the API in string
   *         format. The map will have following Attributes: 1. DATE 2. OPEN
   *         3. HIGH 4. LOW 5. CLOSE 6. VOLUME
   * @throws IllegalArgumentException If no data is found on the given date.
   */
  public Map<String, String> getStockData(String tickerSymbol, Date refDate)
          throws IllegalArgumentException {
    Map<Date, String[]> stockPrice = new HashMap<>();
    try {
      String filedata = new FileIO().readFile(System.getProperty("user.dir") +
              System.getProperty("file.separator") + tickerSymbol + "ApiData.txt");
      stockPrice = convertCSVToMAP(filedata);
    } catch (IOException e) {
      //System.out.println("No previous data found doing an api call");
    }
    Date dateReference = new Date();
    try {
      dateReference = new SimpleDateFormat("yyyy-MM-dd")
              .parse(new SimpleDateFormat("yyyy-MM-dd").format(refDate));
    } catch (ParseException e) {
      //Do nothing since it is a internal conversion and the ref date is already validated
    }
    String[] data = stockPrice.get(dateReference);
    if (data == null) {
      if (getDateDifference(refDate) > 90) {
        String apiData = getStockData(tickerSymbol, "full");
        saveData(apiData, tickerSymbol);
        stockPrice = convertCSVToMAP(apiData);
      } else {
        String apiData = getStockData(tickerSymbol, "compact");
        saveData(apiData, tickerSymbol);
        stockPrice = convertCSVToMAP(apiData);
      }
      if (stockPrice.size() != 0 && !stockPrice.containsKey(dateReference)) {
        throw new IllegalArgumentException("Invalid Date");
      }
      if (stockPrice.get(dateReference) == null) {
        throw new IllegalArgumentException("Error fetching data");
      }
      data = stockPrice.get(dateReference);
    }
    return buildResult(data);
  }

  private String getStockData(String tickerSymbol, String outputSize)
          throws IllegalArgumentException {
    String apiKey = "W0M1JOKC82EZEQA8";
    String stockSymbol = tickerSymbol;
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=" + outputSize
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      StringBuilder response = new StringBuilder();

      while ((b = in.read()) != -1) {
        response.append((char) b);
      }
      if (response.toString().contains(" \"Error Message\"")) {
        throw new IllegalArgumentException("Invalid stock details provided.");
      }
      output.append(response);

    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    return output.toString();
  }

  private Map<Date, String[]> convertCSVToMAP(String csv) {
    Map<Date, String[]> result = new HashMap<>();
    if (csv.isEmpty()) {
      return result;
    }
    String[] lines = csv.split(System.getProperty("line.separator"));
    for (String line : lines) {
      String[] data = line.split(",");
      try {
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(data[0]);
        result.put(d, data);
      } catch (ParseException e) {
        //do nothing since the entry will not be added to map.
      }
    }

    return result;
  }

  private Map<String, String> buildResult(String[] data) {
    Map<String, String> result = new HashMap<>();
    result.put("DATE", data[0]);
    result.put("OPEN", data[1]);
    result.put("HIGH", data[2]);
    result.put("LOW", data[3]);
    result.put("CLOSE", data[4]);
    result.put("VOLUME", data[5]);
    return result;
  }

  private void saveData(String apidata, String tickerSymbol) {
    new FileIO().createFile(apidata, System.getProperty("user.dir"),
            tickerSymbol + "ApiData", true, ".txt");
  }

  private int getDateDifference(Date refDate) {
    long diff = new java.util.Date().getTime() - refDate.getTime();
    return (int) (diff / (24 * 60 * 60 * 1000));
  }
}
