package cps.models;

public class User {
  private int userId;
  private String firstname;
  private String lastname;
  private String email;
  private String password;

  public int getUserId() {
    return this.userId;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public String getEmail() {
    return this.email;
  }

  // new user constructor
  public User(String firstname, String lastname, String email, String password) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
  }

  // user retrieved from database
  public User(int userId, String firstname, String lastname, String email, String password) {
    this.userId = userId;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
  }

  public boolean validatePassword(String password) {
    return this.password.equals(password);
  }
}
