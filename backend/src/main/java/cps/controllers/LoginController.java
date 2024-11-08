package cps.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cps.services.AuthenticationService;
import cps.models.requestBodies.LoginRequestBody;
import cps.models.responseBodies.LoginResponseBody;

@RestController
public class LoginController {

  @PostMapping("/login")
  public ResponseEntity<LoginResponseBody> index(@RequestBody LoginRequestBody loginRequest) {
    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();
    String token;
    try {
      token = AuthenticationService.login(email, password);
      LoginResponseBody loginResponse = new LoginResponseBody(token);
      return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}