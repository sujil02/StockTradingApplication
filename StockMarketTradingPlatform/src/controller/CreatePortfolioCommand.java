package controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import view.IView;

/**
 * Class to handle the the create {@link model.IPortfolio } functionality offered by the
 * application. Its of the type {@link ICommand} thus implementing the first level of
 * functionality.
 */
public class CreatePortfolioCommand implements ICommand {

  @Override
  public void execute(TextController textController, IView view)
          throws IOException, NoSuchElementException {
    try {
      view.append("Enter new Portfolio Name");
      String portfolioName = view.getInput();
      textController.createPortfolio(portfolioName);
      view.append("Portfolio successfully created.");
    } catch (IllegalArgumentException e) {
      view.append("Invalid Entry: " + e.getMessage());
    }
  }

  @Override
  public String gerDescription() {
    return "Create New Portfolio";
  }
}
