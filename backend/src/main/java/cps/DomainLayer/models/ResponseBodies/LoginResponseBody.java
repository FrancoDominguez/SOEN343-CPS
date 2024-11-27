<<<<<<<< HEAD:backend/src/main/java/cps/DTO/ResponseBodies/LoginResponseBody.java
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
========
package cps.DomainLayer.models.ResponseBodies;

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
>>>>>>>> contract:backend/src/main/java/cps/DomainLayer/models/ResponseBodies/LoginResponseBody.java
