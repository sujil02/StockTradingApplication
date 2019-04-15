package Stratergy;

import java.io.IOException;

import controller.IFeatures;
import view.IMainView;


public interface IStrategy {
  void buyStock(IFeatures controller, IMainView view) throws IOException;
}
