package Generate;

import Domain.EventModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class GenerateLocations {
  public EventModel generateLocation() {
    try {
      Random r = new Random();
      FileReader fr = new FileReader("json/locations.json");
      JsonParser jp = new JsonParser();
      JsonObject jo = (JsonObject) jp.parse(fr);
      JsonArray ja = (JsonArray) jo.get("data");
      int i = r.nextInt(ja.size());
      JsonObject location = (JsonObject) ja.get(i);
      String city = location.get("city").toString();
      String country = location.get("country").toString();
      float latitude = location.get("latitude").getAsFloat();
      float longitude = location.get("longitude").getAsFloat();

      return new EventModel(city, country, latitude, longitude);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return new EventModel("err", "err", 0, 0);
  }
}
