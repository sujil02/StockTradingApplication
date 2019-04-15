package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class implements {@link IUser}. This is the implementation of model for
 * the application. The class maintains the list of {@link IPortfolio} created
 * by the user as an ArrayList.
 */
public class UserImpl implements IUser, IUserV2 {

  private String username;
  private List<IPortfolioV2> userPortfolios;

  /**
   * Constructor with no parameters which initialize the user class for default
   * user. Having no portfolios.
   */
  public UserImpl() {
    this.username = "Default";
    userPortfolios = new ArrayList<>();
  }

  /**
   * Construct an object of user with the given username having zero portfolios
   * mapped to its profile.
   *
   * @param username input name of the user.
   * @throws IllegalArgumentException if the username is empty or null.
   */
  public UserImpl(String username) throws IllegalArgumentException {
    if (username != null && !username.isEmpty()) {
      this.username = username;
      userPortfolios = new ArrayList<>();
    } else {
      throw new IllegalArgumentException("Please enter a valid username");
    }
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    if (portfolioName != null && !portfolioName.isEmpty()) {
      IPortfolio newPortfolio = new Portfolio(portfolioName);
      for (IPortfolio portfolio : userPortfolios) {
        if (portfolio.equals(newPortfolio)) {
          throw new IllegalArgumentException("A portfolio with similar name already exist. " +
                  "Please renter.");
        }
      }
      userPortfolios.add(new Portfolio(portfolioName));
    } else {
      throw new IllegalArgumentException("Please enter a valid portfolio name");
    }

  }


  @Override
  public List<String> getAllPortfolioNames() {
    List<String> allPortfolioName = new ArrayList<>();
    for (IPortfolio p : userPortfolios) {
      allPortfolioName.add(p.getPortfolioName());
    }
    return allPortfolioName;
  }

  private IPortfolioV2 getPortfolio(String portfolioName) throws IllegalArgumentException {
    for (IPortfolioV2 portfolio : userPortfolios) {
      if (portfolio.getPortfolioName().equals(portfolioName)) {
        return portfolio;
      }
    }
    throw new IllegalArgumentException("Portfolio does not exist.");
  }

  @Override
  public void makeATrade(String portfolioName, TradeType tradeType, Date date, String tickerSymbol,
                         String companyName, int quantity) {
    IPortfolio portfolio = getPortfolio(portfolioName);
    portfolio.makeATrade(tradeType, date, tickerSymbol, companyName, quantity);
  }

  @Override
  public void makeATrade(String portfolioName, TradeType tradeType, Date date, String tickerSymbol,
                         String companyName, int quantity, float commission) {
    IPortfolioV2 portfolio = getPortfolio(portfolioName);
    portfolio.makeATrade(tradeType, date, tickerSymbol, companyName, quantity, commission);
  }

  @Override
  public void makeATrade(String portfolioName, TradeType tradeType, Date date, String tickerSymbol,
                         String companyName, float investmentAmount, float commission) {
    IPortfolioV2 portfolio = getPortfolio(portfolioName);
    portfolio.makeATrade(tradeType, date, tickerSymbol, companyName, investmentAmount, commission);
  }

  @Override
  public void exportPortfolio(String portfolioName, String path) throws IOException {
    getPortfolio(portfolioName).exportPortfolio(path);
  }

  @Override
  public void importPortfolio(String path) throws IOException
          , IllegalArgumentException {
    IPortfolioV2 importPortfolio = new SerializeAndDeserialize()
            .importPortfolio(path);
    if (userPortfolios.contains(importPortfolio)) {
      throw new IllegalArgumentException("Portfolio with same name already exist");
    }
    userPortfolios.add(importPortfolio);
  }


  @Override
  public IPortfolioV2 getPortfolioContents(String portfolioName) {
    IPortfolioV2 portfolio = getPortfolio(portfolioName);
    IPortfolioV2 portfolioCopy = new Portfolio(portfolio.getPortfolioName());

    return portfolio;
  }

  @Override
  public float getPortfolioCostBias(String portfolioName, Date refDate) {
    IPortfolio portfolio = getPortfolio(portfolioName);
    return portfolio.getPortfolioCostBias(refDate);
  }

  @Override
  public float getPortfolioValue(String portfolioName, Date refDate) {
    IPortfolio portfolio = getPortfolio(portfolioName);
    return portfolio.getPortfolioValue(refDate);
  }


}
