package controller.stratergy;

import java.io.IOException;

import controller.IFeatures;
import view.IMainView;


/**
 * This interface represents different strategy for buying stocks in a stock market. It implements
 * the function of buying stocks in the market by taking in the various parameters from the user.
 * The strategy being used will take control from the controller and the view on which it is
 * currently working to buy stocks adhering to strategy being used and once it completes it action
 * it returns the control back to controller.
 */
public interface IStrategy {
  /**
   * This method performs action of buying stocks/Investing in the market adhering to the strategy
   * being used.
   *
   * @param controller controller from which this strategy is being called.
   * @param view       view being used when this strategy is called.
   */
  void buyStock(IFeatures controller, IMainView view) throws IOException;

  /**
   * This sets the portfolio name on which the strategy should be executed.
   *
   * @param portfolioName existing portfolio name.
   */
  void setPortfolioName(String portfolioName);
}
