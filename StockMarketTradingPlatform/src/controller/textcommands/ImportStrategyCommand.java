package controller.textcommands;

import java.io.IOException;
import java.util.NoSuchElementException;

import controller.TextController;
import view.IView;

public class ImportStrategyCommand implements ICommand {
  @Override
  public void execute(TextController textController, IView view) throws IOException, NoSuchElementException {
    view.append("Enter complete file of input");
    String importFile = view.getInput();
    view.append("Enter new Portfolio Name");
    String portfolioName = view.getInput();
    textController.importStrategy(importFile, portfolioName);
  }

  @Override
  public String gerDescription() {
    return "Import and execute existing strategy";
  }
}
