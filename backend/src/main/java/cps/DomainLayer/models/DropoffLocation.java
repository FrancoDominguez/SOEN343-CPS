package cps.DomainLayer.models;

public class DropoffLocation {
  private String name;
  private String address;

  public DropoffLocation(String name, String address) {
    this.name = name;
    this.address = address;
  }

  public String getName() {
    return this.name;
  }

  public String getAddress() {
    return this.address;
  }

  @Override
  public String toString() {
    return String.format("%s, %s", this.name, this.address);
  }
}
