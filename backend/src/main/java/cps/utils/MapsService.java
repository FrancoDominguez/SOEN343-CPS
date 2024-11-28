package cps.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import static cps.utils.HTTPRequests.makeGetRequestJSON;

public class MapsService {
  private static String url;
  private static String units;
  private static String apiKey;

  static {
    Dotenv dotenv = Dotenv.load();
    url = dotenv.get("GOOGLE_MAPS_API_URL_DISTANCEMATRIX");
    units = "metric";
    apiKey = dotenv.get("GOOGLE_MAPS_API_KEY");
  }

  public static int getDurationDistance(String origin, String destination) {
    try {
      String encodedOrigin = URLEncoder.encode(origin, StandardCharsets.UTF_8);
      String encodedDestination = URLEncoder.encode(destination, StandardCharsets.UTF_8);
      String requestString = String.format("%s?destinations=%s&origins=%s&units=%s&key=%s",
          url, encodedDestination, encodedOrigin, units, apiKey);

      JSONObject response = makeGetRequestJSON(requestString);

      int distanceValue = response
          .getJSONArray("rows")
          .getJSONObject(0)
          .getJSONArray("elements")
          .getJSONObject(0)
          .getJSONObject("distance")
          .getInt("value");
      return distanceValue;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return -1;
    }
  }
}
