import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.IUserV2;
import model.UserImpl;

import static org.junit.Assert.assertEquals;


/**
 * This class represents automated test cases for {@link model.IUserV2}, new
 * features added to the User.
 */
public class IUserV2Test {

  @Test
  public void testMakeAPortfolioByImportFromCreatedFile() {
    IUserV2 user = new UserImpl();
    try {
      user.importPortfolio(System.getProperty("user.dir") +
              System.getProperty("file.separator") + "res" +
              System.getProperty("file.separator") + "Portfolio JSON's" +
              System.getProperty("file.separator") + "hello.json");
      Date refDate = null;
      try {
        refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .parse("2019-04-11 12:00:00");
      } catch (ParseException e) {
        //do nothing
      }
      Float yo = user.getPortfolioCostBias("Default", refDate);
      assertEquals(174941.5, yo, 0.01);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testMakeAPortfolioByImportFromExportedFile() {
    IUserV2 user = new UserImpl();
    try {
      user.importPortfolio(System.getProperty("user.dir") +
              System.getProperty("file.separator") + "res" +
              System.getProperty("file.separator") + "Portfolio JSON's" +
              System.getProperty("file.separator") + "Default.json");
      Date refDate = null;
      try {
        refDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .parse("2019-04-11 12:00:00");
      } catch (ParseException e) {
        //do nothing
      }
      Float yo = user.getPortfolioCostBias("Default", refDate);
      assertEquals(204627.20, yo, 0.01);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testMakeAPortfolioByImportFromFileNotExist() {
    IUserV2 user = new UserImpl();
    try {
      user.importPortfolio(System.getProperty("user.dir") +
              System.getProperty("file.separator") + "res" +
              System.getProperty("file.separator") + "Portfolio JSON's" +
              System.getProperty("file.separator") + "error.json");
      Assert.fail();

    } catch (IOException e) {
      //test case passed
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeAPortfolioByImportFromInvalidJsonFile() {
    IUserV2 user = new UserImpl();
    try {
      user.importPortfolio(System.getProperty("user.dir") +
              System.getProperty("file.separator") + "res" +
              System.getProperty("file.separator") + "Portfolio JSON's" +
              System.getProperty("file.separator") + "error1.json");
      Assert.fail();

    } catch (IOException e) {
      //test case passed
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeAPortfolioByImportFromBlankJsonFile() {
    IUserV2 user = new UserImpl();
    try {
      user.importPortfolio(System.getProperty("user.dir") +
              System.getProperty("file.separator") + "res" +
              System.getProperty("file.separator") + "Portfolio JSON's" +
              System.getProperty("file.separator") + "error2.json");
      Assert.fail();

    } catch (IOException e) {
      //test case passed
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeADuplicatePortfolioFromImport() {
    IUserV2 user = new UserImpl();
    user.createPortfolio("Default");
    try {
      user.importPortfolio(System.getProperty("user.dir") +
              System.getProperty("file.separator") + "res" +
              System.getProperty("file.separator") + "Portfolio JSON's" +
              System.getProperty("file.separator") + "Default.json");
      Assert.fail();

    } catch (IOException e) {
      //test case passed
    }
  }

}
