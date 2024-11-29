package cps.DomainLayer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;
import io.github.cdimascio.dotenv.Dotenv;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



@Service
public class ChatGPTService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static  String API_KEY;
    private ClientService clientService = new ClientService();

   

    static {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("OPENAI_API_KEY");
      }
    

    public String getChatGPTResponse(String userInput, int userId) {
        try {
            // Set up the connection
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");

            

            String systemContext = "You are a chatbot designed to help users with questions about a website called CPS. "
                      + "Only answer questions related to this website. "
                      + "CPS is a delivery system where users can place a delivery, track a delivery, or create a delivery quotation. "
                      + "There are two types of deliveries: Station Dropoff and Home Pickup. "
                      + "In a Station Dropoff, the sender drops a package off at a selected station. "
                      + "In a Home Pickup, a delivery driver picks up the package from the sender at the desired address and time, "
                      + "as chosen by the sender. "
                      + "The sender also has the option to change the pickup time later in the shipping lifecycle. "
                      + "\n\n"
                      + "Here are some common questions and their answers: "
                      + "\n\n"
                      + "1. Who is CPS? "
                      + "A: Canadian Postal Service (CPS) is a shipping company that delivers packages across Canada with reliability and affordability. "
                      + "Established in 1955, CPS is known for its efficient services. "
                      + "\n\n"
                      + "2. What are the normal delivery times for my package? "
                      + "A: Normal delivery takes 3 business days, and expedited delivery takes 2 business days. "
                      + "\n\n"
                      + "3. How do I track my delivery? "
                      + "A: To track your package, enter the provided tracking code into the 'Track your Order' box on the homepage. "
                      + "\n\n"
                      + "4. What services does CPS offer? "
                      + "A: CPS offers station drop-offs at locations across Canada and home pickup services. Fill out the online form for home pickup, "
                      + "and a driver will collect your package. "
                      + "\n\n"
                      + "Remember to always stay focused on helping the user with their CPS-related questions. If a question matches one of the predefined ones, "
                      + "provide the exact response listed above. Otherwise, answer based on your understanding of the CPS website and services."
                      + "\n\n"
                      + "Do not give the user safety critical information, such as client id, delivery id, contract id. Try to simplify the information as much as possible."
                      + "However, you are allowed to provide the tracking ID.";

            ArrayList<Contract> contracts;
            ArrayList<Delivery> deliveries;
            if (userId > 0) {
                contracts = clientService.viewAllActiveContracts(userId);
                deliveries = clientService.viewAllActiveDeliveries(userId);
            
                // Append user-specific data to system context
                StringBuilder userContext = new StringBuilder(systemContext);
                userContext.append("\n\nHere are the contracts for this user, if the user inquires about these contracts, present them in a structured and readable manner:\n");
            
                // Ensure contracts and deliveries are properly formatted as strings
                if (contracts != null && !contracts.isEmpty()) {
                    for (Contract contract : contracts) {
                        // Assuming Contract has a toString method or use custom formatting
                        userContext.append(contract.toString()).append("\n");
                    }
                } else {
                    userContext.append("No active contracts found.\n");
                }
            
                userContext.append("\n\nHere are the active deliveries for this user, if the user inquires about these deliveries, present them in a structured and readable manner:\n");
            
                if (deliveries != null && !deliveries.isEmpty()) {
                    for (Delivery delivery : deliveries) {
                        // Assuming Delivery has a toString method or use custom formatting
                        userContext.append(delivery.toString()).append("\n");
                    }
                } else {
                    userContext.append("No active deliveries found.\n");
                }
            
                // Update systemContext with user-specific information
                systemContext = userContext.toString();
            }

            // Build the payload
            JSONObject payload = new JSONObject();
            payload.put("model", "gpt-4");
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject().put("role", "system").put("content", systemContext));
            messages.put(new JSONObject().put("role", "user").put("content", userInput));
            payload.put("messages", messages);
            payload.put("max_tokens", 150);

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
