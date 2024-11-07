package cps.services;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import cps.utils.Pair;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import static cps.utils.HTTPRequests.makeGetRequestJSON;

public class MapsService {
  private String url;
  private String units;
  private String apiKey;

  public MapsService() {
    Dotenv dotenv = Dotenv.load();
    this.url = dotenv.get("GOOGLE_MAPS_API_URL_DISTANCEMATRIX");
    this.units = "metric";
    this.apiKey = dotenv.get("GOOGLE_MAPS_API_KEY");
  }

  public Pair<Integer, Integer> getDurationDistance(String origin, String destination) {
    try {
      String encodedOrigin = URLEncoder.encode(origin, StandardCharsets.UTF_8);
      String encodedDestination = URLEncoder.encode(destination, StandardCharsets.UTF_8);
      String requestString = String.format("%s?destinations=%s&origins=%s&units=%s&key=%s",
          this.url, encodedDestination, encodedOrigin, this.units, this.apiKey);
      JSONObject response = makeGetRequestJSON(requestString);
      int durationValue = response
          .getJSONArray("rows")
          .getJSONObject(0)
          .getJSONArray("elements")
          .getJSONObject(0)
          .getJSONObject("duration")
          .getInt("value");
      int distanceValue = response
          .getJSONArray("rows")
          .getJSONObject(0)
          .getJSONArray("elements")
          .getJSONObject(0)
          .getJSONObject("distance")
          .getInt("value");
      return new Pair<Integer, Integer>(durationValue, distanceValue);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
