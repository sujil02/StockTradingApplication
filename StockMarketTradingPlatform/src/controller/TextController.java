package controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import controller.textcommands.BuyUsingDollarCostStrategyCommand;
import controller.textcommands.CreatePortfolioCommand;
import controller.textcommands.ExaminePortfolioCommand;
import controller.textcommands.ICommand;
import controller.textcommands.ImportPortfolioCommand;
import controller.textcommands.ImportStrategyCommand;
import model.IUserV2;
import view.IView;

/**
 * TextController class which takes inputs from the user and tells the model what to do and the view
 * what to show.
 */
public class TextController extends AbstractController {
  private final IView view;
  private Map<Integer, ICommand> commands;

  /**
   * Constructs an object of the controller with the passed model and view objects from the main
   * execution thread.
   *
   * @param user Represents the model type.
   * @param view View implementing the {@link IView} resposible for the I/O operation.
   * @throws IllegalArgumentException If any of the input parameters are given as null.
   */
  public TextController(IUserV2 user, IView view) throws IllegalArgumentException {
    super(user);
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
    super.view = view;
    commands = new LinkedHashMap<>();
    commands.put(1, new CreatePortfolioCommand());
    commands.put(2, new ExaminePortfolioCommand());
    commands.put(3, new BuyUsingDollarCostStrategyCommand());
    commands.put(4, new ImportPortfolioCommand());
    commands.put(5, new ImportStrategyCommand());
  }

  @Override
  public void start() {
    try {
      while (true) {
        view.append("Select from following options");
        for (int i : commands.keySet()) {
          view.append(i + "\t" + commands.get(i).gerDescription());
        }
        view.append(commands.size() + 1 + "\tQuit");
        view.append("Select option");
        int option;
        while (true) {
          try {
            option = Integer.parseInt(view.getInput());
            break;
          } catch (NumberFormatException e) {
            view.append("Invalid option. Try Again.");
          }
        }
        if (option == commands.size() + 1) {
          break;
        }
        commands.get(option).execute(this, view);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Error in writing output");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Error in reading input");
    }
  }
}
