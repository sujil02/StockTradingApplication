package controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import controller.Stratergy.DollarCostStrategy;
import controller.Stratergy.IStrategy;
import model.IUserV2;
import model.TradeType;
import view.IMainView;
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
    super.view = view;
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
  public void importStrategy(String path, String portfolioName, IMainView view) throws IOException {

  }

  public void executeStrategy(IFeatures features) throws IOException {
    Map<String, Object> parameters = this.view.getStrategyFields();
    String portfolioName = (String) parameters.get("portfolioName");
    TradeType type = TradeType.BUY;
    Map<String, Float> tickerSymbols = (Map<String, Float>) parameters.get("tickerSymbols");
    float investmentAmount = (float) parameters.get("investmentAmount");
    float commission = (float) parameters.get("commission");
    Date startDate = (Date) parameters.get("startDate");
    Date endDate = (Date) parameters.get("endDate");
    int freq = (int) parameters.get("frequency");

    try {
      super.setStrategy(DollarCostStrategy.getStrategyBuilder().setPortfolioName(portfolioName)
              .setTradeType(type).setTickerSymbolsAndProportions(tickerSymbols)
              .setInvestmentAmount(investmentAmount).setCommission(commission)
              .setDuration(startDate, endDate, freq).build());
      super.executeStrategy(this);
    } catch (IOException e) {
      view.showErrorMessage("Error creating Strategy " + e.getMessage());
    }

  }
}
