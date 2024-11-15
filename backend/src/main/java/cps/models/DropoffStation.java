package cps.models;

public class DropoffStation extends Location {
  private String name;

  public DropoffStation(String streetAddress, String postalCode, String city, String country, String name) {
    super(streetAddress, postalCode, city, country);
    this.name = name;
  }

  public DropoffStation(int id, String streetAddress, String postalCode, String city, String country, String name) {
    super(id, streetAddress, postalCode, city, country);
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
