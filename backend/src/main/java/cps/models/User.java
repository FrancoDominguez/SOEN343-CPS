package cps.models;

public class User {
  private int userId;
  private String firstname;
  private String lastname;
  private String email;
  private String password;

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
}
