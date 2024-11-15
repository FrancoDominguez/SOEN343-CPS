package cps.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.models.RequestBodies.LoginRequestBody;
import cps.models.ResponseBodies.BasicResponse;
import cps.models.ResponseBodies.LoginResponseBody;
import cps.utils.AuthenticationService;

@RestController
public class LoginController {

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/login")
  public ResponseEntity<BasicResponse> login(@RequestBody LoginRequestBody loginRequest) {
    // take in the request body in the parameters ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^
    // each request body needs its own class to match the json format you receive
    // for this endpoint I made one called LoginRequestBody
    try {
      String email = loginRequest.getEmail();
      String password = loginRequest.getPassword();

      // this is the body of the request, it will execute the main functionality
      String token = AuthenticationService.login(email, password);
      String message = "Login successful";

      // I specifically made a class to return the jwt to make this cleaner and easier
      LoginResponseBody loginResponse = new LoginResponseBody(message, token);

      // In this example I return a ResponseEntity so I can include the status code
      return new ResponseEntity<BasicResponse>(loginResponse, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      BasicResponse response = new BasicResponse(e.getMessage());
      return new ResponseEntity<BasicResponse>(response, HttpStatus.UNAUTHORIZED);
    }
  }
}