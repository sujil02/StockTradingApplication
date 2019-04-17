package SerializeAndDeserialize;

import java.io.IOException;

import controller.Stratergy.IStrategy;
import model.IPortfolioV2;

/**
 * Any serialization and deserialization supported by the model has to be processed fulfilling this
 * contract. Current application support serialization and deserialization of portfolio to json
 * however this can we further scaled by other representations implementing this contract.
 */
public interface ISerializeAndDeserialize {

  /**
   * This functionality imports the given portfolio.
   * @param path location where the file needs to be exported.
   * @return Portfolio object.
   * @throws IOException if the file is not found at the location.
   */
  public IPortfolioV2 importPortfolio(String path) throws IOException;

  /**
   * This allows the user to export the given portfolio in a text based json file.
   * @param portfolio Portfolio object.
   * @param path path where portfolio is to be exported.
   */
  public void exportPortfolio(IPortfolioV2 portfolio, String path);

  void exportStrategy(IStrategy strategy, String path);

  IStrategy importStrategy(String path) throws IOException;
}
