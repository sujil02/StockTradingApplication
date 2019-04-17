package model;

import java.util.Date;
import java.util.Map;

/**
 * This method implements IStock. It represents a stock by its company name and
 * its ticker symbol. ticker symbol is assumed to be unique for a particular
 * company. Hence companies with same ticker symbol are considered same.
 */
public class Stock implements IStock {
  private String companyName;
  private String tickerSymbol;
  private int quantity;
  private float costPrice;
  private Date purchaseDate;
  private float commission;

  /**
   * This method initialises an object of Stock with provided company name and
   * ticker symbol and the provided properties of a trade. company name can be
   * empty but ticker symbol is mandatory.
   *
   * @param companyName  name of company
   * @param tickerSymbol unique ticker symbol of the company
   * @param quantity     number of shares bought
   * @param purchaseDate purchase date of stock.
   * @param commission   commission paid on a trade.
   * @throws IllegalArgumentException If company name or ticker symbol is null.
   *                                  If tickerSymbol is empty. If quantity or
   *                                  cost price is less than or equal to 0. If
   *                                  purchase Date is null.
   */
  public Stock(String companyName, String tickerSymbol, int quantity,
               Date purchaseDate, float commission)
          throws IllegalArgumentException {
    if (companyName == null || tickerSymbol == null || tickerSymbol.isEmpty()) {
      throw new IllegalArgumentException("Company Name or Ticker Symbol can not be null or empty.");
    }
    if (quantity <= 0 || commission < 0) {
      throw new IllegalArgumentException("Quantity or costPrice can not be less than " +
              "or equal to 0 or commission less than 0");
    }
    if (purchaseDate == null) {
      throw new IllegalArgumentException("Invalid Purchase Date");
    }
    this.quantity = quantity;
    this.purchaseDate = purchaseDate;
    this.commission = commission;
    this.companyName = companyName;
    this.tickerSymbol = tickerSymbol.toUpperCase();
  }

  /**
   * This method initialises an object of Stock with provided company name and
   * ticker symbol and the provided properties of a trade. quantity is set to 0.
   * company name can be empty but ticker symbol is mandatory.
   *
   * @param companyName  name of company
   * @param tickerSymbol unique ticker symbol of the company
   * @param purchaseDate purchase date of stock.
   * @param commission   commission paid on a trade.
   * @throws IllegalArgumentException If company name or ticker symbol is null.
   *                                  If tickerSymbol is empty. If quantity or
   *                                  cost price is less than or equal to 0. If
   *                                  purchase Date is null.
   */
  public Stock(String companyName, String tickerSymbol, Date purchaseDate,
               float commission) throws IllegalArgumentException {
    if (companyName == null || tickerSymbol == null || tickerSymbol.isEmpty()) {
      throw new IllegalArgumentException("Company Name or Ticker Symbol " +
              "can not be null or empty.");
    }
    if (commission < 0) {
      throw new IllegalArgumentException("commission can not be less than " +
              "or equal to 0 or commission less than 0");
    }
    if (purchaseDate == null) {
      throw new IllegalArgumentException("Invalid Purchase Date");
    }
    this.quantity = 0;
    this.purchaseDate = purchaseDate;
    this.commission = commission;
    this.companyName = companyName;
    this.tickerSymbol = tickerSymbol.toUpperCase();
  }

  /**
   * This method initialises an object of Stock with provided company name and
   * ticker symbol and the provided properties of a trade. quantity is set to 0.
   * company name can be empty but ticker symbol is mandatory.
   *
   * @param companyName  name of company
   * @param tickerSymbol unique ticker symbol of the company
   * @param purchaseDate purchase date of stock.
   * @param commission   commission paid on a trade.
   * @param costPrice    Price of the stock.
   * @throws IllegalArgumentException If company name or ticker symbol is null.
   *                                  If tickerSymbol is empty. If quantity or
   *                                  cost price is less than or equal to 0. If
   *                                  purchase Date is null.
   */
  public Stock(String companyName, String tickerSymbol, int quantity, float costPrice,
               Date purchaseDate, float commission) throws IllegalArgumentException {

    if (companyName == null || tickerSymbol == null || tickerSymbol.isEmpty()) {
      throw new IllegalArgumentException("Company Name or Ticker Symbol can not be null or empty.");
    }
    if (quantity <= 0 || commission < 0) {
      throw new IllegalArgumentException("Quantity or costPrice can not be less than " +
              "or equal to 0 or commission less than 0");
    }
    if (purchaseDate == null) {
      throw new IllegalArgumentException("Invalid Purchase Date");
    }
    this.quantity = quantity;
    this.purchaseDate = purchaseDate;
    this.costPrice = costPrice;
    this.commission = commission;
    this.companyName = companyName;
    this.tickerSymbol = tickerSymbol.toUpperCase();
  }

  @Override
  public float getValue(Date refDate) throws IllegalArgumentException {
    if (refDate == null) {
      throw new IllegalArgumentException("Date Cannot be null.");
    }

    IStockDataAPI stockData = new StockDataAPI();

    Map<String, String> data = stockData.getStockData(this.tickerSymbol, refDate);
//    float hi = Float.parseFloat(data.get("HIGH"));
//    float lo = Float.parseFloat(data.get("LOW"));


    //return (hi + lo) / 2;
    return Float.parseFloat(data.get("CLOSE"));
  }

  public void setCostPrice() {
    this.costPrice = getValue(purchaseDate);
  }


  public String getCompanyName() {
    return this.companyName;
  }

  public String getTickerSymbol() {
    return this.tickerSymbol;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Stock)) {
      return false;
    }

    return this.tickerSymbol.equals(((Stock) obj).tickerSymbol);
  }

  @Override
  public String toString() {
    return "Stock{" +
            "companyName='" + companyName + '\'' +
            ", tickerSymbol='" + tickerSymbol + '\'' +
            ", quantity=" + quantity + '\'' +
            ", costPrice=" + costPrice + '\'' +
            ", purchaseDate=" + purchaseDate + '\'' +
            ", commission=" + commission + '\'' +
            '}';
  }

  @Override
  public int hashCode() {
    return this.tickerSymbol.hashCode();
  }

  public int getQuantity() {
    return this.quantity;
  }

  public float getCostPrice() {
    return this.costPrice;
  }

  public float getCostBias() {
    return (this.costPrice * this.quantity) + this.commission;
  }

  public Date getPurchaseDate() {
    return this.purchaseDate;
  }

  public float getCommission() {
    return this.commission;
  }

  public String getAttributes() {
    return quantity + "\t" + getCostBias() + "\t" + purchaseDate.toString();
  }

  /**
   * This validated the quantity while making a purchase such that atleast one
   * stock could be bought for the invested amount.
   */
  public void setQuantity(float investmentAmount) {
    if (this.costPrice == 0) {
      setCostPrice();
    }
    int quant = (int) (investmentAmount / costPrice);
    if (quant == 0) {
      throw new IllegalArgumentException("Investment Too Low");
    }
    this.quantity = quant;
  }

  @Override
  public IStock copy() {
    return new Stock(companyName, tickerSymbol, quantity,
            costPrice, purchaseDate, commission);
  }
}
