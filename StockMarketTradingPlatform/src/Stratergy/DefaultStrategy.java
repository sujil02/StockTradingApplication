package Stratergy;

import java.io.IOException;
import java.util.Date;

import controller.IFeatures;
import model.TradeType;
import view.IMainView;

public class DefaultStrategy implements IStrategy {
  private String portfolioName;
  private TradeType tradeType;
  private Date date;
  private String tickerSymbol;
  private String companyName;
  private int quantity;
  private float commission;
  private float investmentAmount;

  private DefaultStrategy(String portfolioName, TradeType tradeType, Date date, String tickerSymbol
          , String companyName, int quantity, float commission, float investmentAmount) {
    this.portfolioName = portfolioName;
    this.tradeType = tradeType;
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
  public void export() {

  }

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

    public DefaultStrategyBuilder setPortfolioName(String portfolioName) {
      this.portfolioName = portfolioName;
      return this;
    }

    public DefaultStrategyBuilder setTradeType(TradeType tradeType) {
      this.tradeType = tradeType;
      return this;
    }

    public DefaultStrategyBuilder setDate(Date date) {
      this.date = date;
      return this;
    }

    public DefaultStrategyBuilder setTickerSymbol(String tickerSymbol) {
      this.tickerSymbol = tickerSymbol;
      return this;
    }

    public DefaultStrategyBuilder setCompanyName(String companyName) {
      this.companyName = companyName;
      return this;
    }

    public DefaultStrategyBuilder setQuantity(int quantity) {
      this.quantity = quantity;
      this.investmentAmount = 0;
      return this;
    }

    public DefaultStrategyBuilder setCommission(float commission) {
      this.commission = commission;
      return this;
    }

    public DefaultStrategyBuilder setInvestmentAmount(float investmentAmount) {
      this.investmentAmount = investmentAmount;
      this.quantity = 0;
      return this;
    }

    public IStrategy build() {
      return new DefaultStrategy(portfolioName, tradeType, date, tickerSymbol, companyName,
              quantity, commission, investmentAmount);
    }
  }

}
