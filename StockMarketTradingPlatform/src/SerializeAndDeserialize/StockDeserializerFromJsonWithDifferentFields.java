package SerializeAndDeserialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.IStock;
import model.Stock;

/**
 * This class is used as a reference mapping by the gson library to parse the Stock json object.
 */
public class StockDeserializerFromJsonWithDifferentFields implements JsonDeserializer<IStock> {

  @Override
  public IStock deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context)
          throws JsonParseException {
    JsonObject jObject = jElement.getAsJsonObject();
    String companyName = jObject.get("companyName").getAsString();
    String tickerSymbol = jObject.get("tickerSymbol").getAsString();
    int quantity = jObject.get("quantity").getAsInt();
    float costPrice = jObject.get("costPrice").getAsFloat();
    String purchaseDate = jObject.get("purchaseDate").getAsString();
    float commission = jObject.get("commission").getAsFloat();

    Date refDate = new Date();
    try {
      refDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
              .parse(purchaseDate);
    } catch (ParseException e) {
      //do nothing
    }
    return new Stock(companyName, tickerSymbol, quantity, costPrice, refDate, commission);
  }
}