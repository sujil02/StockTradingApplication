import org.junit.Test;


import model.IUser;
import model.UserImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class implements automated test cases for validation of {@link IUser}
 * interface.
 */
public class UserImplTest {

  @Test
  public void createPortfolio() {
    IUser user = new UserImpl("Sujil");
    user.createPortfolio("First portfolio");
    user.createPortfolio("Second portfolio");
    assertEquals("[First portfolio, Second portfolio]",
            user.getAllPortfolioNames().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createPortfolioDuplicate() {
    IUser user = new UserImpl("Sujil");
    user.createPortfolio("First portfolio");
    user.createPortfolio("First portfolio");
  }

  @Test
  public void getAllPortfolios() {
    IUser user = new UserImpl("Sujil");
    user.createPortfolio("First portfolio");
    user.createPortfolio("Second portfolio");
    user.createPortfolio("Third portfolio");
    user.createPortfolio("Forth portfolio");
    assertEquals("[First portfolio, Second portfolio, Third portfolio, Forth portfolio]",
            user.getAllPortfolioNames().toString());
  }


  @Test
  public void getAllPortfoliosDefaultUser() {
    IUser user = new UserImpl();
    user.createPortfolio("First portfolio");
    user.createPortfolio("Second portfolio");
    user.createPortfolio("Third portfolio");
    user.createPortfolio("Forth portfolio");
    assertEquals("[First portfolio, Second portfolio, Third portfolio, Forth portfolio]",
            user.getAllPortfolioNames().toString());
  }


  @Test(expected = IllegalArgumentException.class)
  public void InvalidUserName() {
    IUser user = new UserImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void InvalidUserNameEmpty() {
    IUser user = new UserImpl("");
  }

}