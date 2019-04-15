package view;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * This is the view class contract, responsible for any kind of user interaction like accepting
 * input from the screen or displaying input on the screen.
 */
public interface IView extends IMainView {
  /**
   * Return an object of type {@link java.lang.Readable}.
   *
   * @return {@link java.lang.Readable}.
   * @throws NoSuchElementException if the object is not initialized.
   */
  String getInput() throws NoSuchElementException;

  /**
   * return an object of type {@link java.lang.Appendable} of this class.
   *
   * @param output {@link java.lang.Appendable}.
   * @throws IOException if the object is no longer accessible.
   */
  void append(String output) throws IOException;

}
