package view;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This is the view class, responsible for any kind of user interaction like accepting input from
 * the screen or displaying input on the screen.
 */
public class View implements IView {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor of the view class takes objects of type {@link java.lang.Readable} and {@link
   * java.lang.Appendable}.
   *
   * @param in  an object of type {@link java.lang.Readable}.
   * @param out an object of type {@link java.lang.Appendable}.
   */
  public View(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Input and Output can not be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public String getInput() throws NoSuchElementException {
    return scan.nextLine();

  }

  @Override
  public void append(String output) throws IOException {
    out.append(output);
    out.append("\n");
  }
}
