package cps.models;

import cps.DAO.ClientDAO;

public class ClientModel {
  private int id;
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private Location homeAddress;

  public ClientModel(String firstname, String lastname, String email, String password, String streetAddress,
      String postalCode, String city, String country) {
    this.id = -1; // -1 indicates a new user
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.homeAddress = new Location(streetAddress, postalCode, city, country);
  }

  public ClientModel(int id, String firstname, String lastname, String email, String password, String streetAddress,
      String postalCode, String city, String country) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.homeAddress = new Location(streetAddress, postalCode, city, country);
  }

  public ClientModel(String firstname, String lastname, String email, String password) {
    // update this ^^^
    this.id = -1;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
  }

  public ClientModel(int id, String firstname, String lastname, String email, String password) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
  }

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

  public String getPassword() {
    return this.password;
  }

  public Location getHomeAddress() {
    return this.homeAddress;
  }

  public boolean validatePassword(String password) {
    return this.password.equals(password);
  }

  public void setHomeAddress(Location location) {
    this.homeAddress = location;
  }

  public void save() throws Exception {
    ClientDAO clientDAO = new ClientDAO();
    if (this.id == -1) {
      clientDAO.insert(this);
    } else {
      clientDAO.update(this);
    }
  }
}