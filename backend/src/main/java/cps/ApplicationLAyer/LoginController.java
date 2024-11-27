package cps.ApplicationLayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.DomainLayer.Services.AuthenticationService;
import cps.DTO.RequestBodies.LoginRequestBody;
import cps.DTO.ResponseBodies.BasicResponse;
import cps.DTO.ResponseBodies.LoginResponseBody;

@RestController
public class LoginController {

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequestBody loginRequest) {
    System.out.println("Processing login request...\n");
    try {
      String email = loginRequest.getEmail();
      String password = loginRequest.getPassword();

      String token = AuthenticationService.login(email, password);
      String message = "Login successful";

      LoginResponseBody loginResponse = new LoginResponseBody(message, token);

      System.out.println("... Login request completed\n");
      return new ResponseEntity<Object>(loginResponse, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      BasicResponse response = new BasicResponse(e.getMessage());
      return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
    }
  }
}