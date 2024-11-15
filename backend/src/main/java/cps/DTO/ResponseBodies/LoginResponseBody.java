package cps.DTO.ResponseBodies;

public class LoginResponseBody extends BasicResponse {
  private String token;
  private String message;

  public LoginResponseBody(String message, String token) {
    super(message);
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  public String getMessage() {
    return this.message;
  }
}
