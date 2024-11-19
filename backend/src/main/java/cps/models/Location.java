package cps.models;

public class Location {
  private int id;
  private String streetAddress;
  private String postalCode;
  private String city;
  private String country;

  public Location(String streetAddress, String postalCode, String city, String country) {
    this.id = -1;
    this.streetAddress = streetAddress;
    this.postalCode = postalCode;
    this.city = city;
    this.country = country;
  }

  public Location(int id, String streetAddress, String postalCode, String city, String country) {
    this.id = id;
    this.streetAddress = streetAddress;
    this.postalCode = postalCode;
    this.city = city;
    this.country = country;
  }

  public int getId() {
    return this.id;
  }

  public String getStreetAddress() {
    return this.streetAddress;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public String getCity() {
    return this.city;
  }

  public String getCountry() {
    return this.country;
  }

  @Override
  public String toString() {
    return String.format("%s, %s, %s, %s", streetAddress, postalCode, city, country);
  }
}