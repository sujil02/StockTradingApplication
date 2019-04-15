package controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import view.IView;

/**
 * This class contains implementation to execute the procedure which imports {@link
 * model.IPortfolio} for this user.
 */
public class ImportPortfolioCommand implements ICommand {
  @Override
  public void execute(TextController textController, IView view)
          throws IOException, NoSuchElementException {
    try {
      view.append("Enter complete file of input");
      String importFile = view.getInput();
      textController.importPortfolio(importFile);
      view.append("Import Successful.");
    } catch (IOException e) {
      view.append("File Error. " + e.getMessage());
    }
  }

  @Override
  public String gerDescription() {
    return "Import Existing portfolio";
  }
}
