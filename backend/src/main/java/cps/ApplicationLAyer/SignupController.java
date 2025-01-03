package cps.ApplicationLayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.DomainLayer.Services.AuthenticationService;
import cps.DTO.RequestBodies.SignupRequestbody;

@RestController
public class SignupController {

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody SignupRequestbody signupRequest) {
    System.out.println("Processing signup request...\n");
    try {
      AuthenticationService.createUser(signupRequest);

      // Return a success message
      System.out.println("Signup request completed\n");
      return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("User registration failed", HttpStatus.BAD_REQUEST);
    }
  }
}