import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.IPortfolioV2;
import model.IUserV2;
import model.TradeType;

/**
 * This class is a mock user class created to test {@link controller.IController} to verify
 * controller receives proper inputs from view and view provides outputs provided by controller.
 */
public class MockUser implements IUserV2 {
  private float unicode;
  private String testOutputString;
  private StringBuilder log;
  private IPortfolioV2 portfolio;

  /**
   * This method creates an instance of MockUser. This method accepts a unicode and testOutput
   * string which will be returned for numerical and string return types respectively.
   *
   * @param unicode          a unique number identifying model and to verify numerical outputs
   * @param testOutputString a test string to verify string output
   * @param log              StringBuilder to log the input received from controller.
   */
  public MockUser(float unicode, String testOutputString, StringBuilder log) {
    this.unicode = unicode;
    this.testOutputString = testOutputString;
    this.log = log;
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    log.append("PortfolioName Entered: " + portfolioName + "\n");
  }

  @Override
  public List<String> getAllPortfolioNames() {
    List<String> result = new ArrayList<>();
    result.add(testOutputString);
    return result;
  }

  @Override
  public void makeATrade(String portfolioName, TradeType tradeType, Date date, String tickerSymbol
          , String companyName, int quantity) {
    log.append("PortfolioName: " + portfolioName
            + ", TradeType: " + tradeType
            + ", Date: " + date.toString()
            + ", TickerSymbol: " + tickerSymbol
            + ", CompanyName: " + companyName
            + ", Quantity: " + quantity
            + "\n");
  }

  @Override
  public void makeATrade(String portfolioName, TradeType tradeType, Date date, String tickerSymbol
          , String companyName, int quantity, float commission) {
    log.append("PortfolioName: " + portfolioName
            + ", TradeType: " + tradeType
            + ", Date: " + date.toString()
            + ", TickerSymbol: " + tickerSymbol
            + ", CompanyName: " + companyName
            + ", Quantity: " + quantity
            + ", Commission " + commission
            + "\n");
  }


  @Override
  public void makeATrade(String portfolioName, TradeType tradeType, Date date,
                         String tickerSymbol, String companyName,
                         float investmentAmount, float commission) {
    log.append("PortfolioName: " + portfolioName
            + ", TradeType: " + tradeType
            + ", Date: " + date.toString()
            + ", TickerSymbol: " + tickerSymbol
            + ", CompanyName: " + companyName
            + ", Investment Amount: " + investmentAmount
            + ", Commission " + commission
            + "\n");
  }


  @Override
  public void exportPortfolio(String portfolioName, String path) throws IOException {
    log.append("PortfolioName Entered: " + portfolioName + "\n");
  }

  @Override
  public void importPortfolio(String path) throws IOException {
    log.append("Path Entered: " + path + "\n");
  }

  @Override
  public IPortfolioV2 getPortfolioContents(String portfolioName) {
    log.append("PortfolioName Entered: " + portfolioName + "\n");
    return portfolio;
  }

  @Override
  public float getPortfolioCostBias(String portfolioName, Date refDate) {
    log.append("PortfolioName Entered: " + portfolioName
            + ", refDate: " + refDate
            + "\n");
    return unicode;
  }

  @Override
  public float getPortfolioValue(String portfolioName, Date refDate) {
    log.append("PortfolioName Entered: " + portfolioName
            + ", refDate: " + refDate
            + "\n");
    return unicode;
  }
}
