package cps.DomainLayer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



@Service
public class ChatGPTService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static  String API_KEY;

   

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("OPENAI_API_KEY");
      }
    

      public String getChatGPTResponse(String userInput) {
        try {
            // Set up the connection
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
    
            // Build the payload
            JSONObject payload = new JSONObject();
            payload.put("model", "gpt-4");
            payload.put("temperature", 0.2); // Low temperature for deterministic responses
            payload.put("max_tokens", 150);
    
            JSONArray messages = new JSONArray();
    
            // System-level instruction for domain-specific behavior
            messages.put(new JSONObject().put("role", "system").put("content",
                "You are a helpful assistant for the Canadian Postal Service (CPS). " +
                "You provide accurate, concise answers to CPS-related questions. " +
                "Topics include delivery times, tracking, shipping services, and station locations. " +
                "If the query is unrelated to CPS, politely inform the user and avoid detailed responses."));
    
            // Add user input to the conversation
            messages.put(new JSONObject().put("role", "user").put("content", userInput));
            payload.put("messages", messages);
    
            // Send the request
            OutputStream os = conn.getOutputStream();
            os.write(payload.toString().getBytes());
            os.flush();
    
            // Read the response
            StringBuilder response = new StringBuilder();
            if (conn.getResponseCode() == 200) {
                try (var br = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
            } else {
                return "Error: Unable to fetch response from ChatGPT";
            }
            conn.disconnect();
    
            // Parse the response
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
    
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
}
