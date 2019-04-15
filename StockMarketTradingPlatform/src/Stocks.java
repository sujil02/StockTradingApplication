import java.io.InputStreamReader;

import controller.GUIController;
import controller.IController;
import controller.TextController;
import model.IUserV2;
import model.UserImpl;
import view.IView;
import view.View;

/**
 * This class contains the main method of the application. This class is the start point of the
 * application.
 */
public class Stocks {
  /**
   * This is the main method of the application which gets called on execution of the application.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    IUserV2 user = new UserImpl("Default");
    if (args.length > 0) {
      if (args[0].toUpperCase().equals("TEXT")) {
        IView view = new View(new InputStreamReader(System.in), System.out);
        IController controller = new TextController(user, view);
        controller.start();
      }
    } else {
      IController guiController = new GUIController(user);
      guiController.start();
    }

  }
}
