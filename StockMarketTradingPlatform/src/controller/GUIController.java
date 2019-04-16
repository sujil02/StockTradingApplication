package controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import Stratergy.DollarCostStrategy;
import Stratergy.IStrategy;
import model.IUserV2;
import model.TradeType;
import view.guiview.IJFrameView;
import view.guiview.JFrameView;

/**
 * GUI Controller class which takes inputs from the user and tells the model what to do and the view
 * what to show. Basically decides which operations to be called over the model based on the inputs
 * from the view.
 */
public class GUIController extends AbstractController {
  private IJFrameView view;
  private IStrategy strategy;

  public GUIController(IUserV2 model) {
    super(model);
    strategy = null;
    setView();
  }

  /**
   * Method to start the whole application platform from the main method.
   */
  @Override
  public void start() {
    //Void GUI start application implementation.
  }

  public void setView() {
    view = new JFrameView();
    view.setFeatures(this);
  }

  @Override
  public boolean createPortfolio(String portfolioName) {
    try {
      super.createPortfolio(portfolioName);
      view.showSuccessMessage("Portfolio created successfully");
    } catch (IllegalArgumentException e) {
      view.showErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public boolean validatePortfolioName(String portfolioName) {
    if (super.validatePortfolioName(portfolioName)) {
      view.hideUserPane();
      view.showManagePortfolioPane(portfolioName);
    } else {
      view.showErrorMessage("Portfolio Does Not Exist");
      return false;
    }
    return true;
  }

  @Override
  public boolean buyStocks(String portfolioName, TradeType tradeType,
                           Date date, String tickerSymbol,
                           String companyName, int quantity, float commission) {
    try {
      super.buyStocks(portfolioName, tradeType, date, tickerSymbol,
              companyName, quantity, commission);
    } catch (IllegalArgumentException e) {
      view.showErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public boolean buyStocks(String portfolioName, TradeType tradeType,
                           Date date, String tickerSymbol,
                           String companyName, float investmentAmount, float commission) {
    try {
      super.buyStocks(portfolioName, tradeType, date, tickerSymbol,
              companyName, investmentAmount, commission);
    } catch (IllegalArgumentException e) {
      view.showErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public void exportPortfolio(String portfolioName, String path) {
    try {
      super.exportPortfolio(portfolioName, path);
      view.showSuccessMessage("Exported Successfully");
    } catch (IOException e) {
      view.showErrorMessage("File Error");
    }
  }

  @Override
  public void importPortfolio(String path) {
    try {
      super.importPortfolio(path);
      view.showSuccessMessage("Imported Successfully");
    } catch (IOException e) {
      view.showErrorMessage("File Error");
    } catch (IllegalArgumentException ee) {
      view.showErrorMessage("Portfolio with similar name already exist or Improper Json format");
    }
  }

  @Override
  public void exportStrategy(String path) throws IOException {

  }

  @Override
  public void importStrategy(String path) throws IOException {

  }

  public void executeStratergy(String portfolioName, TradeType type, Map<String, Float> tickerSymbols
          , int totalQuantity, float investmentAmount, float commission, Date startDate,
                               Date endDate, int freq) {
    try {
      strategy = DollarCostStrategy.getStrategyBuilder().setPortfolioName(portfolioName)
              .setTradeType(type).setTickerSymbolsAndProportions(tickerSymbols)
              .setTotalQuantity(totalQuantity).setInvestmentAmount(investmentAmount)
              .setCommission(commission).setDuration(startDate, endDate, freq).build();
      strategy.buyStock(this, view);
    } catch (IOException e) {
      view.showErrorMessage("Error creating Strategy " + e.getMessage());
    }

  }
}
