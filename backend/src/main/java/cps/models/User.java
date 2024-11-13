package cps.models;

public class User {
  private int id;
  private String firstname;
  private String lastname;
  private String email;
  private String password;

  public int getId() {
    return this.id;
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
    this.id = -1;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
  }

  // user retrieved from database
  public User(int id, String firstname, String lastname, String email, String password) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
  }

  public boolean validatePassword(String password) {
    return this.password.equals(password);
  }

  public void save() {
    if (this.id == -1) {
      String queryString = "";
      // create new user
    } else {
      String queryString = "";
      // update values to mysql

    }
  }
}
