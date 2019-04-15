package controller.textcommands;

import java.io.IOException;
import java.util.NoSuchElementException;

import controller.TextController;
import view.IView;

/**
 * This is an contract following the command design pattern. This pattern unifies different sets of
 * operations under one umbrella, so that they can be treated uniformly. This interface mainly
 * handles two broad scope commands of the application which are create or make transactions on
 * {@link model.IPortfolio}.
 * <p>Make transactions on a portfolio is further handled by {@link IPortfolioCommand}.</p>
 */
public interface ICommand {
  /**
   * This method makes it mandatory for the implementing classes to execute the necessary steps to
   * ensure the appropriate action is done.
   *
   * @param textController Object of model.
   * @param view           Object of view.
   * @throws IOException            if an I/O error occurs.
   * @throws NoSuchElementException if method params are inaccessible.
   */
  void execute(TextController textController, IView view)
          throws IOException, NoSuchElementException;

  /**
   * Returns the description of what this command does.
   *
   * @return string representation of this commands activity.
   */
  String gerDescription();
}
