package cps.models;

public class Address {
  private String streetAddress;
  private String postalCode;
  private String city;
  private String country;

  // Constructor
  public Address(String streetAddress, String postalCode, String city, String country) {
    this.streetAddress = streetAddress;
    this.postalCode = postalCode;
    this.city = city;
    this.country = country;
  }

  // Getters
  public String getStreetAddress() {
    return streetAddress;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  // Setters
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  // toString method
  @Override
  public String toString() {
    return "Address{" +
        "streetAddress='" + streetAddress + '\'' +
        ", postalCode='" + postalCode + '\'' +
        ", city='" + city + '\'' +
        ", country='" + country + '\'' +
        '}';
  }
}