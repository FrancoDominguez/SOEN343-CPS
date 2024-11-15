package cps.models;

public class Parcel {
  private static double maxLength;
  private int id;
  private double length;
  private double width;
  private double height;
  private double weight;
  private Boolean isFragile;

  static {
    maxLength = 48;
  }

  public Parcel(double length, double width, double height, double weight, Boolean isFragile) {
    this.id = -1;
    this.length = length;
    this.width = width;
    this.height = height;
    this.weight = weight;
    this.isFragile = isFragile;
  }

  public Parcel(int id, double length, double width, double height, double weight, Boolean isFragile) {
    this.id = id;
    this.length = length;
    this.width = width;
    this.height = height;
    this.weight = weight;
    this.isFragile = isFragile;
  }

  public int getId() {
    return this.id;
  }

  public double getLength() {
    return this.length;
  }

  public double getWidth() {
    return this.width;
  }

  public double getHeight() {
    return this.height;
  }

  public double getWeight() {
    return this.weight;
  }

  public Boolean getIsFragile() {
    return this.isFragile;
  }

  public Boolean isOversized() {
    return (this.length > maxLength || this.width > maxLength || this.height > maxLength);
  }
}