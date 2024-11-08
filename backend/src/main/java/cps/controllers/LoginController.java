package cps.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cps.services.AuthenticationService;

@RestController
public class LoginController {

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> index(@RequestBody LoginRequest loginRequest) {
    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();
    String token;
    try {
      token = AuthenticationService.login(email, password);
      LoginResponse loginResponse = new LoginResponse(token);
      return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  private class LoginRequest {
    private String email;
    private String password;

    public String getEmail() {
      return email;
    }

    public String getPassword() {
      return password;
    }
  }

  private class LoginResponse {
    private String token;

    public LoginResponse(String token) {
      this.token = token;
    }

    public String getToken() {
      return this.token;
    }
  }
}