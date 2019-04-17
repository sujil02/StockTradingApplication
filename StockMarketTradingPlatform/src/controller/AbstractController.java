package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import SerializeAndDeserialize.SerializeAndDeserialize;
import controller.Stratergy.IStrategy;
import model.IPortfolioV2;
import model.IUserV2;
import model.TradeType;
import view.IMainView;

/**
 * This class abstracts out the funtionalities offered by the controller. The model object and all
 * its functionalitites are accessed from here directly and whichever controller extending this
 * can use to communicate with the model.
 */
public abstract class AbstractController implements IController, IFeatures {
  protected IUserV2 model;
  protected IStrategy strategy;
  protected IMainView view;

  protected AbstractController(IUserV2 model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public boolean buyStocks(String portfolioName, TradeType tradeType,
                           Date date, String tickerSymbol,
                           String companyName, int quantity, float commission) {
    model.makeATrade(portfolioName, tradeType, date, tickerSymbol, companyName, quantity
            , commission);
    return true;
  }

  @Override
  public boolean buyStocks(String portfolioName, TradeType tradeType,
                           Date date, String tickerSymbol,
                           String companyName, float investmentAmount, float commission) {
    model.makeATrade(portfolioName, tradeType, date, tickerSymbol, companyName, investmentAmount
            , commission);
    return true;
  }

  @Override
  public boolean createPortfolio(String portfolioName) {
    model.createPortfolio(portfolioName);
    return true;
  }

  @Override
  public List<String> getAllPortfolioNames() {
    return model.getAllPortfolioNames();
  }

  @Override
  public IPortfolioV2 getPortfolioContents(String portfolioName) {
    return model.getPortfolioContents(portfolioName);
  }

  @Override
  public float getPortfolioCostBias(String portfolioName, Date ref) {
    return model.getPortfolioCostBias(portfolioName, ref);
  }

  @Override
  public float getPortfolioValue(String portfolioName, Date ref) {
    return model.getPortfolioValue(portfolioName, ref);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public boolean validatePortfolioName(String portfolioName) {
    List<String> portfolioNames = getAllPortfolioNames();
    return portfolioNames.contains(portfolioName);
  }

  @Override
  public void exportPortfolio(String portfolioName, String path)
          throws IOException {
    model.exportPortfolio(portfolioName, path);
  }

  @Override
  public void importPortfolio(String path) throws IOException {
    model.importPortfolio(path);
  }

  public void exportStrategy(String path) throws IOException {
    SerializeAndDeserialize serializeAndDeserialize = new SerializeAndDeserialize();
//    Map<String,Float> stringDoubleMap = new HashMap<>();
//    stringDoubleMap.put("GOOG",Float.valueOf(10));
//    strategy = new DollarCostStrategy("aa", TradeType.BUY, stringDoubleMap, 5, 5, 5, new Date(), new Date(),5);
    serializeAndDeserialize.exportStrategy(strategy, path);
  }

  public void importStrategy(String path, String portfolioName) throws IOException {
    IStrategy strategy;
    //TODO import strategy
    if (!validatePortfolioName(portfolioName)) {
      createPortfolio(portfolioName);
    }
    getStrategy().setPortfolioName(portfolioName);
    executeStrategy(this);


  }

  public void executeStrategy(IFeatures features) throws IOException {
    strategy.buyStock(features, view);
  }

  public void setStrategy(IStrategy strategy) {
    this.strategy = strategy;
  }

  public IStrategy getStrategy() {
    return strategy;
  }


}
