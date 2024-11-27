package cps.DomainLayer.models;

public class Station extends Location {
  private String name;

  public Station(String streetAddress, String postalCode, String city, String country, String name) {
    super(streetAddress, postalCode, city, country);
    this.name = name;
  }

  public Station() {
  }

  public Station(int id, String streetAddress, String postalCode, String city, String country, String name) {
    super(id, streetAddress, postalCode, city, country);
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return "Station [Name: " + name +
        ", Address: " + super.toString() + "]";
  }
}
