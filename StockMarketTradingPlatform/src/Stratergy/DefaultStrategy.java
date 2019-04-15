package Stratergy;

import java.io.IOException;
import java.util.Date;

import controller.IFeatures;
import model.TradeType;
import view.IMainView;
import view.IView;

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

  private static class DefaultStrategyBuilder {
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

    public void setPortfolioName(String portfolioName) {
      this.portfolioName = portfolioName;
    }

    public void setTradeType(TradeType tradeType) {
      this.tradeType = tradeType;
    }

    public void setDate(Date date) {
      this.date = date;
    }

    public void setTickerSymbol(String tickerSymbol) {
      this.tickerSymbol = tickerSymbol;
    }

    public void setCompanyName(String companyName) {
      this.companyName = companyName;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
      this.investmentAmount = 0;
    }

    public void setCommission(float commission) {
      this.commission = commission;
    }

    public void setInvestmentAmount(float investmentAmount) {
      this.investmentAmount = investmentAmount;
      this.quantity = 0;
    }

    public IStrategy build() {
      return new DefaultStrategy(portfolioName, tradeType, date, tickerSymbol, companyName,
              quantity, commission, investmentAmount);
    }
  }

}
