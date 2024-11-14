package cps.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.models.RequestBodies.SignupRequestbody;
import cps.models.User;

@RestController
public class SignupController {

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody SignupRequestbody signupRequest) {
    try {
      // Extract details from the request
      String firstname = signupRequest.getFirstname();
      String lastname = signupRequest.getLastname();
      String email = signupRequest.getEmail();
      String password = signupRequest.getPassword();

      // Create a new User object
      User newUser = new User(firstname, lastname, email, password);

      // Save the user to the database
      newUser.save();

      // Return a success message
      return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    } catch (Exception e) {
      // Log the error and return a failure response
      e.printStackTrace();
      return new ResponseEntity<>("User registration failed", HttpStatus.BAD_REQUEST);
    }
  }
}