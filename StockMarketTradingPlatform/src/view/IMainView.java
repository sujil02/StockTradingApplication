package view;

import java.io.IOException;

/**
 * Main view interface that allows this application to be scalled further if and new view
 * application needs to be support can extend this view thus ensure basic contract features.
 */
public interface IMainView {
  void showErrorMessage(String message) throws IOException;

  void showSuccessMessage(String message) throws IOException;
}
