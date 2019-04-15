package controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import view.IView;

/**
 * This is a contract for all the commands that could be executed over a portfolio. An operation
 * that needs to executed over a portfolio has to implement the following functions.
 */
public interface IPortfolioCommand {
  /**
   * This method act as a controller acting in a specific manner for this given portfolio. Though
   * the portfolio once selected from the {@link ICommand} type commands is passed as a parameter to
   * the implementation.
   *
   * @param textController Model representing the user on which this command is to be executed.
   * @param view           view object to handle the I/O communications of the command.
   * @param portfolioName  Name of the {@link model.IPortfolio} on which this operation is to be
   *                       performed.
   * @throws IOException            If the IO operations on the view fails.
   * @throws NoSuchElementException If model is not able to process some information.
   */
  void execute(TextController textController, IView view, String portfolioName)
          throws IOException, NoSuchElementException;

  /**
   * This method provides the description as what the command is doing.
   *
   * @return String representation of its function.
   */
  String gerDescription();
}
