package cps.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import cps.exceptions.FailedGetRequestException;

public final class HTTPRequests {
  private HTTPRequests() {
  };

  public static JSONObject makeGetRequestJSON(String requestUrl) throws Exception, FailedGetRequestException {
    try {
      System.out.printf("Attempting Request to %s%n", requestUrl);
      URL url = new URL(requestUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();

        JSONObject responseJson = new JSONObject(content.toString());
        int indentation = 2;
        System.out.println(responseJson.toString(indentation));
        return responseJson;
      } else {
        throw new FailedGetRequestException("GET request failed: " + responseCode);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    }
  }
}
