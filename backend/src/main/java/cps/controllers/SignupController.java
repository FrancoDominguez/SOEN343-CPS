package cps.controllers;

import org.springframework.web.bind.annotation.RequestBody;

import cps.models.RequestBodies.SignupRequestbody;
import cps.models.User;

public class SignupController {

  public void signup(@RequestBody SignupRequestbody signupRequest) {
    String firstname = signupRequest.getFirstname();
    String lastname = signupRequest.getLastname();
    String email = signupRequest.getEmail();
    String password = signupRequest.getPassword();
    User newUser = new User(firstname, lastname, email, password);
    

  }
}
