<<<<<<<< HEAD:backend/src/main/java/cps/DTO/RequestBodies/SignupRequestbody.java
package cps.DTO.RequestBodies;

public class SignupRequestbody {
  private String email;
  private String firstname;
  private String lastname;
  private String password;

  public String getEmail() {
    return email;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getPassword() {
    return password;
  }
========
package cps.DomainLayer.models.RequestBodies;

public class SignupRequestbody {
  private String email;
  private String firstname;
  private String lastname;
  private String password;

  public String getEmail() {
    return email;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getPassword() {
    return password;
  }
>>>>>>>> contract:backend/src/main/java/cps/DomainLayer/models/RequestBodies/SignupRequestbody.java
}