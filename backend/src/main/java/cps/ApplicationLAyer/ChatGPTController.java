package cps.ApplicationLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cps.DomainLayer.ChatGPTService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatGPTController {

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping
    public ResponseEntity<?> getResponse(@RequestBody Map<String, String> input) {
        String userInput = input.get("message");
        String response = chatGPTService.getChatGPTResponse(userInput);

        // Wrap the response in a JSON object
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("assistant", response);

        return ResponseEntity.ok(jsonResponse);
    }
}

