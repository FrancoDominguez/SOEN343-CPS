package cps.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import cps.models.AuthenticatedUser;

@RestController
public class LoginController {

  @PostMapping("/login")
  public AuthenticatedUser index() {

    return null;
  }

}
