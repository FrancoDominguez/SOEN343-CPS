package cps.models;

public class Station extends Location {
  private String name;

  public Station(String streetAddress, String postalCode, String city, String country, String name) {
    super(streetAddress, postalCode, city, country);
    this.name = name;
  }

  public Station(int id, String streetAddress, String postalCode, String city, String country, String name) {
    super(id, streetAddress, postalCode, city, country);
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
