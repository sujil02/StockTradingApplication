package controller.stratergy;

import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.util.Date;

import controller.IFeatures;
import model.TradeType;
import view.IMainView;

/**
 * The strategy is defined to buy stocks/ invest in the market. However buying single stocks is also
 * a strategy where no complex logic is involved. This class implements {@link IStrategy} to
 * represents strategy of buying single stocks one at a time. This is the default strategy since it
 * could be used as fallback when no strategy would be used.
 */
public class DefaultStrategy implements IStrategy {
  private String portfolioName;
  @Expose
  private Date date;
  @Expose
  private String tickerSymbol;
  @Expose
  private String companyName;
  @Expose
  private int quantity;
  private float commission;
  private float investmentAmount;

  private DefaultStrategy(String portfolioName, TradeType tradeType, Date date, String tickerSymbol
          , String companyName, int quantity, float commission, float investmentAmount) {
    this.portfolioName = portfolioName;
    TradeType tradeType1 = tradeType;
    this.date = date;
    this.tickerSymbol = tickerSymbol;
    this.companyName = companyName;
    this.quantity = quantity;
    this.commission = commission;
    this.investmentAmount = investmentAmount;
  }

  public static DefaultStrategyBuilder getDefaultStrategyBuilder() {
    return new DefaultStrategyBuilder();
  }

  @Override
  public void buyStock(IFeatures textController, IMainView view) throws IOException {
    try {
      if (quantity != 0) {
        textController.buyStocks(portfolioName, TradeType.BUY, date, tickerSymbol
                , companyName, quantity, commission);
      } else {
        textController.buyStocks(portfolioName, TradeType.BUY, date, tickerSymbol
                , companyName, investmentAmount, commission);
      }
      view.showSuccessMessage("Trade completed successfully.");
    } catch (IllegalArgumentException e) {
      view.showErrorMessage("Transaction could not be completed.\n" + e.getMessage());
    }
  }

  @Override
  public void setPortfolioName(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  /**
   * This class represents builder for {@link DefaultStrategy}. It initialises all the parameters
   * required by {@link DefaultStrategy} to a default value which can be modified using the methods
   * provided.
   */
  public static class DefaultStrategyBuilder {
    private String portfolioName;
    private TradeType tradeType;
    private Date date;
    private String tickerSymbol;
    private String companyName;
    private int quantity;
    private float commission;
    private float investmentAmount;

    private DefaultStrategyBuilder() {
      this.portfolioName = "";
      this.tradeType = TradeType.BUY;
      this.date = new Date();
      this.tickerSymbol = "";
      this.companyName = "";
      this.quantity = 0;
      this.commission = 0;
      this.investmentAmount = 0;
    }

    /**
     * Sets the portfolio name for strategy.
     */
    public DefaultStrategyBuilder setPortfolioName(String portfolioName) {
      this.portfolioName = portfolioName;
      return this;
    }

    /**
     * Sets TradeType of strategy.
     */
    public DefaultStrategyBuilder setTradeType(TradeType tradeType) {
      this.tradeType = tradeType;
      return this;
    }

    /**
     * Sets Transaction date of strategy.
     */
    public DefaultStrategyBuilder setDate(Date date) {
      this.date = date;
      return this;
    }

    /**
     * Sets ticker symbol of the stock which will be bought.
     */

    public DefaultStrategyBuilder setTickerSymbol(String tickerSymbol) {
      this.tickerSymbol = tickerSymbol;
      return this;
    }

    /**
     * Sets the company name involved in the trade.
     */
    public DefaultStrategyBuilder setCompanyName(String companyName) {
      this.companyName = companyName;
      return this;
    }

    /**
     * Sets the quantity of the stocks bought. if quantity is greater than zero it will reset the
     * investment amount to 0.
     */
    public DefaultStrategyBuilder setQuantity(int quantity) {
      if (quantity > 0) {
        this.quantity = quantity;
        this.investmentAmount = 0;
      } else {
        this.quantity = 0;
      }
      return this;
    }

    /**
     * Sets the commission paid on the trade.
     */
    public DefaultStrategyBuilder setCommission(float commission) {
      this.commission = commission;
      return this;
    }

    /**
     * Sets the amount to be invested. If the amount is greater than 0 method will set quantity to
     * zero.
     */
    public DefaultStrategyBuilder setInvestmentAmount(float investmentAmount) {
      if (investmentAmount > 0) {
        this.investmentAmount = investmentAmount;
        this.quantity = 0;
      } else {
        this.investmentAmount = 0;
      }
      return this;
    }

    /**
     * creates the strategy object based on the parameters provided.
     *
     * @return Default Strategy object
     */
    public IStrategy build() {
      return new DefaultStrategy(portfolioName, tradeType, date, tickerSymbol, companyName,
              quantity, commission, investmentAmount);
    }
  }

}
