package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import controller.Stratergy.IStrategy;
import model.IPortfolioV2;
import model.TradeType;

/**
 * This interface represents a set of features that the program offers. Each feature is exposed as a
 * function in this interface. This function is used suitably as a callback by the view, to pass
 * control to the controller. How the view uses them as callbacks is completely up to how the view
 * is designed (e.g. it could use them as a callback for a button, or a callback for a dialog box,
 * or a set of text inputs, etc.). Each function is designed to take in the necessary data to
 * complete that functionality.
 */
public interface IFeatures {

  /**
   * This method performs buy stock action on model for the given portfolio for the given trade
   * attributes.
   *
   * @param portfolioName portfolio name on which trade is performed
   * @param tradeType     type of trade performed
   * @param date          date on which trade was performed
   * @param tickerSymbol  ticker symbol of the company in trade
   * @param companyName   name of company in trade
   * @param quantity      number of stocks in trade
   * @param commission    commission paid for the trade
   * @return true if trade is successful.
   */
  boolean buyStocks(String portfolioName, TradeType tradeType, Date date,
                    String tickerSymbol, String companyName, int quantity,
                    float commission);

  /**
   * This method performs buy stock action on model for the given portfolio for the given trade
   * attributes.
   *
   * @param portfolioName    portfolio name on which trade is performed
   * @param tradeType        type of trade performed
   * @param date             date on which trade was performed
   * @param tickerSymbol     ticker symbol of the company in trade
   * @param companyName      name of company in trade
   * @param investmentAmount Amount invested in the trade
   * @param commission       commission paid for the trade
   * @return true if trade is successful.
   */
  boolean buyStocks(String portfolioName, TradeType tradeType, Date date,
                    String tickerSymbol, String companyName,
                    float investmentAmount, float commission);

  /**
   * Creates portfolio in model of the given portfolio name.
   *
   * @param portfolioName name of portfolio being created
   * @return true if portfolio is created successfully.
   */
  boolean createPortfolio(String portfolioName);

  /**
   * This method gets all portfolios in {@link model.IUserV2}.
   *
   * @return list of portfolio names
   */
  List<String> getAllPortfolioNames();

  /**
   * The method provides the contents of the portfolio with given portfolio name.
   *
   * @param portfolioName name of portfolio for which contents are required.
   * @return portfolio copy of the requested portfolio.
   */
  IPortfolioV2 getPortfolioContents(String portfolioName);

  /**
   * The method calculates cost bias of the given portfolio.
   *
   * @param portfolioName name of portfolio whose cost bias needs to be calculated.
   * @param ref           reference date on which cost bias needs to be calculated.
   * @return cost bias of the portfolio on the given date.
   */
  float getPortfolioCostBias(String portfolioName, Date ref);

  /**
   * A feature that contains implementation to execute the procedure to calculate the total value of
   * this {@link model.IPortfolio} linked to this user.
   *
   * @param portfolioName Name of the portfolio on which the total value is needed.
   * @param ref           reference date on which value is needed.
   */
  float getPortfolioValue(String portfolioName, Date ref);

  /**
   * Feature to check if the portfolio name is valid.
   *
   * @param portfolioName name of the portfolio to be validated.
   * @return boolean response.
   */
  boolean validatePortfolioName(String portfolioName);

  /**
   * Feature to export a given portfolio.
   */
  void exportPortfolio(String portfolioName, String path) throws IOException;

  /**
   * Feature to import a selected portfolio into the user profile.
   *
   * @param path Location from where the file is to be read.
   * @throws IOException If file is not found.
   */
  void importPortfolio(String path) throws IOException;


  /**
   * Exit the program.
   */
  void exitProgram();


  void exportStrategy(String path) throws IOException;

  /**
   * This method imports a strategy from a file and executes it on a given portfolio. If the given
   * portfolio name exists, the strategy will be implemented on the existing portfolio. Else, it
   * will create a new portfolio with the given name and execute strategy on the new portfolio.
   *
   * @param path          Location from where the file is to be read.
   * @param portfolioName name of the portfolio on which the strategy will be executed.
   * @throws IOException If file is not found.
   */
  void importStrategy(String path, String portfolioName) throws IOException;

  /**
   * This method executes the strategy on the set parameters.
   *
   * @param features instance of control to take control of the application.
   */
  void executeStrategy(IFeatures features) throws IOException;

  /**
   * This method sets the strategy to be used.
   *
   * @param strategy strategy which should be used.
   */
  void setStrategy(IStrategy strategy);

  /**
   * This method provides the current strategy object.
   *
   * @return In-Use strategy
   */
  IStrategy getStrategy();
}
