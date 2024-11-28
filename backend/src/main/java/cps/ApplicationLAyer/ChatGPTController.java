package cps.ApplicationLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cps.DomainLayer.*;

@RestController
@RequestMapping("/api/chat")
public class ChatGPTController {

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping
    public ResponseEntity<?> getResponse(@RequestBody String userInput) {
        String response = chatGPTService.getChatGPTResponse(userInput);
        return ResponseEntity.ok(response);
    }
}
