package controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import view.IView;

/**
 * This java class is responsible to execute functionality which is used to display all the
 * portfolios having the user.
 */
public class DisplayPortfoliosCommand implements ICommand {
  @Override
  public void execute(TextController textController, IView view)
          throws IOException, NoSuchElementException {
    List<String> portfolioNames = textController.getAllPortfolioNames();
    if (portfolioNames.size() == 0) {
      view.append("No portfolios available for this user.");
    } else {
      view.append("Following is the list of portfolios:");
      for (String portfolioName : portfolioNames) {
        view.append(portfolioName);
      }
    }
  }

  @Override
  public String gerDescription() {
    return "Display all portfolio names.";
  }
}
