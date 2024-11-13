package cps.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cps.services.AuthenticationService;
import cps.models.RequestBodies.LoginRequestBody;
import cps.models.responseBodies.LoginResponseBody;

@RestController
public class LoginController {

  @PostMapping("/login")
  public ResponseEntity<LoginResponseBody> login(@RequestBody LoginRequestBody loginRequest) {
    // take in the request body in the parameters ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^
    // each request body needs its own class to match the json format you receive
    // for this endpoint I made one called LoginRequestBody
    try {
      String email = loginRequest.getEmail();
      String password = loginRequest.getPassword();

      // this is the body of the request, it will execute the main functionality
      String token = AuthenticationService.login(email, password);

      // I specifically made a class to return the jwt to make this cleaner and easier
      LoginResponseBody loginResponse = new LoginResponseBody(token);

      // In this example I return a ResponseEntity so I can include the status code
      return new ResponseEntity<LoginResponseBody>(loginResponse, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}
