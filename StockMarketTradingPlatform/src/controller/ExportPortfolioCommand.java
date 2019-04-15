package controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import view.IView;

/**
 * This class contains implementation to execute the procedure which exports selected {@link
 * model.IPortfolio} linked to this user.
 */
public class ExportPortfolioCommand implements IPortfolioCommand {
  @Override
  public void execute(TextController textController, IView view, String portfolioName)
          throws IOException, NoSuchElementException {
    try {
      view.append("Enter directory path for export");
      String exportPath = view.getInput();
      textController.exportPortfolio(portfolioName, exportPath);
      view.append("Export Successful");
    } catch (IOException e) {
      view.append("Error exporting data. " + e.getMessage());
    }
  }

  @Override
  public String gerDescription() {
    return "Export Current Portfolio";
  }
}
