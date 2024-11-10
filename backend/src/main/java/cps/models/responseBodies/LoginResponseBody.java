package cps.models.ResponseBodies;

public class LoginResponseBody {
  private String token;

  public LoginResponseBody(String token) {
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }
}
