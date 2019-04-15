package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements IPortfolio using IStock. It uniquely identifies each
 * portfolio by its portfolio name.
 */
public class Portfolio implements IPortfolio, IPortfolioV2 {


  private String portfolioName;
  List<IStock> ownedStocks;

  /**
   * This method creates an portfolio object uniquely identified by its name.
   *
   * @param portfolioName portfolio name
   * @throws IllegalArgumentException if portfolioName is null or empty
   */
  public Portfolio(String portfolioName) throws IllegalArgumentException {
    if (portfolioName == null || portfolioName.isEmpty()) {
      throw new IllegalArgumentException("Portfolio Name can not be null or empty");
    }
    this.portfolioName = portfolioName;
    this.ownedStocks = new LinkedList<>();
  }

  private void addStock(String companyName, String tickerSymbol, int quantity, Date purchaseDate)
          throws IllegalArgumentException {
    if (tickerSymbol.isEmpty()) {
      throw new IllegalArgumentException("TicketSymbol cannot be empty");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity or Cost Price can not be negative or zero");
    }
    IStock stockName = new Stock(companyName, tickerSymbol, quantity, purchaseDate, 0);
    stockName.setCostPrice();
    ownedStocks.add(stockName);

  }

  private void addStock(String companyName, String tickerSymbol,
                        int quantity, Date purchaseDate, float commission)
          throws IllegalArgumentException {
    if (tickerSymbol.isEmpty()) {
      throw new IllegalArgumentException("TicketSymbol cannot be empty");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity or Cost Price can not be negative or zero");
    }
    IStock stockName = new Stock(companyName, tickerSymbol, quantity, purchaseDate, commission);
    stockName.setCostPrice();
    ownedStocks.add(stockName);
  }

  private void addStock(String companyName, String tickerSymbol,
                        float investmentAmount, Date purchaseDate, float commission)
          throws IllegalArgumentException {
    if (tickerSymbol.isEmpty()) {
      throw new IllegalArgumentException("TicketSymbol cannot be empty");
    }
    if (investmentAmount <= 0) {
      throw new IllegalArgumentException("Investment Amount can not be negative or zero");
    }

    IStock stockName = new Stock(companyName, tickerSymbol, purchaseDate, commission);
    stockName.setCostPrice();
    stockName.setQuantity(investmentAmount);
    ownedStocks.add(stockName);
  }

  @Override
  public float getPortfolioCostBias(Date refDate) throws IllegalArgumentException {
    if (refDate == null) {
      throw new IllegalArgumentException("Date can not be null");
    }
    float costBias = 0;
    for (IStock s : ownedStocks) {
      if (s.getPurchaseDate().before(refDate)) {
        costBias += s.getCostBias();
      }
    }
    return costBias;
  }

  @Override
  public float getPortfolioValue(Date refDate) throws IllegalArgumentException {
    if (refDate == null) {
      throw new IllegalArgumentException("Date can not be null");
    }
    float value = 0;
    for (IStock s : ownedStocks) {
      if (s.getPurchaseDate().before(refDate)) {
        value += s.getQuantity() * s.getValue(refDate);
      }
    }
    return value;
  }

  @Override
  public String getPortfolioContents() {
    StringBuilder portfolioContents = new StringBuilder();
    for (IStock s : ownedStocks) {
      portfolioContents.append(s.getCompanyName() + "\t" + s.getTickerSymbol() + "\t"
              + s.getAttributes() + "\n");
    }
    String contents = portfolioContents.toString();
    if (contents.length() > 1) {
      return contents.substring(0, contents.length() - 1);
    } else {
      return contents;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Portfolio)) {
      return false;
    }

    return this.portfolioName.equals(((Portfolio) obj).portfolioName);
  }

  @Override
  public int hashCode() {
    return this.portfolioName.hashCode();
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }

  @Override
  public void makeATrade(TradeType tradeType, Date date, String ticker, String stockName,
                         int quantity) {
    if (!isDateInOpenMarketSession(date)) {
      throw new IllegalArgumentException("Invalid Purchase Date");
    }
    switch (tradeType) {
      case BUY:
        addStock(stockName, ticker, quantity, date);
        return;
      case SELL:
        return;
      default:
        throw new IllegalArgumentException("Invalid Trade Type");
    }
  }

  @Override
  public void makeATrade(TradeType tradeType, Date date, String ticker, String stockName,
                         int quantity, float commission) {
    if (!isDateInOpenMarketSession(date)) {
      throw new IllegalArgumentException("Invalid Purchase Date");
    }
    switch (tradeType) {
      case BUY:
        addStock(stockName, ticker, quantity, date, commission);
        return;
      case SELL:
        return;
      default:
        throw new IllegalArgumentException("Invalid Trade Type");
    }
  }

  @Override
  public void makeATrade(TradeType tradeType, Date date, String ticker, String stockName,
                         float investmentAmount, float commission) {
    if (!isDateInOpenMarketSession(date)) {
      throw new IllegalArgumentException("Invalid Purchase Date");
    }
    switch (tradeType) {
      case BUY:
        addStock(stockName, ticker, investmentAmount, date, commission);
        return;
      case SELL:
        return;
      default:
        throw new IllegalArgumentException("Invalid Trade Type");
    }
  }

  private boolean isDateInOpenMarketSession(Date purchaseDate) {
    if (purchaseDate == null) {
      return false;
    }
    Date currentDate = new Date();
    if (purchaseDate.after(currentDate)) {
      return false;

    }
    SimpleDateFormat localTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
    String purDate = localTimeFormat.format(purchaseDate);

    String startTime = purDate + " 09:00:00";
    String endTime = purDate + " 16:00:00";

    Date start = new Date();
    Date end = new Date();
    try {
      start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
      end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
    } catch (ParseException e) {
      //Do nothing Since date should be always parsed successfully as it is
      // create inside the function
    }
    if (purchaseDate.before(start) || purchaseDate.after(end)) {
      return false;
    }

    String day = new SimpleDateFormat("EEEE").format(purchaseDate);

    return !(day.equals("Saturday") || day.equals("Sunday"));
  }

  /**
   * Exports the portfolio contents as a JSON file.
   *
   * @param path Path where the portfolio should be exported.
   * @throws IllegalArgumentException If given portfolio name is not found.
   */
  @Override
  public void exportPortfolio(String path) throws IllegalArgumentException {
    SerializeAndDeserialize serializeAndDeserialize = new SerializeAndDeserialize();
    serializeAndDeserialize.exportPortfolio(this, path);
  }

  @Override
  public List<IStock> getOwnedStocks() {
    //return ownedStocks;
    List<IStock> result = new LinkedList<>();
    for (IStock s : ownedStocks) {
      result.add(s.copy());
    }
    return result;
  }


}
