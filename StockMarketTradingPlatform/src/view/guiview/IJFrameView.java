package view.guiview;

import java.util.Map;

import controller.IFeatures;
import view.IMainView;

/**
 * This is the interface which represents the functionalities offered by the {@link javax.swing} GUI
 * view of this application.
 */
public interface IJFrameView extends IMainView {
  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param features the set of feature callbacks as a Features object
   */
  void setFeatures(IFeatures features);

  /**
   * Represents a way for the controller to communicate errormessages to the user.
   *
   * @param message error messages to be displayed.
   */
  void showErrorMessage(String message);

  /**
   * Represents a way for the controller to communicate successful operation to the user.
   *
   * @param message error messages to be displayed.
   */
  void showSuccessMessage(String message);

  void hideUserPane();

  void showManagePortfolioPane(String portfolioName);

  public void hideManagePane();

  public void showUserPane();

  Map<String,Object> getStrategyFields();


}
