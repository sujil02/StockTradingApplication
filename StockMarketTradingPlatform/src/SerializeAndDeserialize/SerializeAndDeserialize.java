package SerializeAndDeserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import controller.Stratergy.DollarCostStrategy;
import controller.Stratergy.IStrategy;
import model.FileIO;
import model.IPortfolioV2;
import model.IStock;
import model.Portfolio;

/**
 * This class handles the serialization and deserialization of java objects. It mainly uses the
 * google GSON jar to convert the given java object in the for of a json string ahd gson type
 * adapter to convert json string to java objects.
 */
public class SerializeAndDeserialize implements ISerializeAndDeserialize {

  @Override
  public IPortfolioV2 importPortfolio(String path) throws IOException
          , IllegalArgumentException {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(IStock.class,
            new StockDeserializerFromJsonWithDifferentFields());
    gsonBuilder.registerTypeAdapter(Portfolio.class,
            new PortfolioDeserializerFromJsonWithDifferentFields());
    Gson gson = gsonBuilder.setPrettyPrinting().create();
    String data = new FileIO().readFile(path);
    if (data.isEmpty()) {
      throw new IllegalArgumentException("File ca not be empty");
    }
    try {
      return gson.fromJson(data, Portfolio.class);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error reading file, File format error.");
    }
  }

  @Override
  public void exportPortfolio(IPortfolioV2 portfolio, String path) {

    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("MM/dd/yyyy HH:mm:ss");
    gsonBuilder.disableHtmlEscaping();
    Gson gson = gsonBuilder.setPrettyPrinting().create();
    String jsonString = gson.toJson(portfolio);
    new FileIO().createFile(jsonString, path, portfolio.getPortfolioName(), false, ".json");
  }

  public void exportStrategy(IStrategy strategy, String path) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("MM/dd/yyyy HH:mm:ss");
    gsonBuilder.disableHtmlEscaping();
    gsonBuilder.excludeFieldsWithoutExposeAnnotation();
    Gson gson = gsonBuilder.setPrettyPrinting().create();
    String jsonString = gson.toJson(strategy);
    String fileName = "Strategy_" + new SimpleDateFormat("yyyyMMddkkmmss").format(new Date());
    new FileIO().createFile(jsonString, path, fileName, false, ".json");
  }

  @Override
  public IStrategy importStrategy(String path) throws IOException {

    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(IStrategy.class,
            new StockDeserializerFromJsonWithDifferentFields());
    gsonBuilder.setDateFormat("MM/dd/yyyy HH:mm:ss");
    Gson gson = gsonBuilder.setPrettyPrinting().create();
    String data = new FileIO().readFile(path);
    if (data.isEmpty()) {
      throw new IllegalArgumentException("File ca not be empty");
    }
    try {
      return gson.fromJson(data, DollarCostStrategy.class);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error reading file, File format error.");
    }
  }
}