package cps.models.ResponseBodies;

public class LoginResponseBody {
  private String token;
  private String message;

  public LoginResponseBody(String message, String token) {
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  public String getMessage() {
    return this.message;
  }
}
