package cps.models.responseBodies;

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
