package serializedeserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import model.IPortfolioV2;
import model.IStock;
import model.Portfolio;

/**
 * This class is used as a reference mapping by the gson library to parse the {@link IPortfolioV2}
 * json object.
 */
public class PortfolioDeserializerFromJsonWithDifferentFields
        implements JsonDeserializer<Portfolio> {

  @Override
  public Portfolio deserialize(JsonElement jElement, Type typeOfT,
                               JsonDeserializationContext context)
          throws JsonParseException {
    JsonObject jObject = jElement.getAsJsonObject();
    String portfolioName = jObject.get("portfolioName").getAsString();
    JsonArray stocks = jObject.get("ownedStocks").getAsJsonArray();
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(IStock.class,
            new StockDeserializerFromJsonWithDifferentFields());
    Gson gson = gsonBuilder.setPrettyPrinting().create();
    List<IStock> iStocks = gson.fromJson(stocks, new TypeToken<LinkedList<IStock>>() {
    }.getType());
    Portfolio portfolio = new Portfolio(portfolioName);
    portfolio.setOwnedStocks(iStocks);
    return portfolio;
  }
}