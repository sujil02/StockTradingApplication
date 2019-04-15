package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

/**
 * This class handles the serialization and deserialization of java objects. It
 * mainly uses the google GSON jar to convert the given java object in the for
 * of a json string ahd gson type adapter to convert json string to java
 * objects.
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
}