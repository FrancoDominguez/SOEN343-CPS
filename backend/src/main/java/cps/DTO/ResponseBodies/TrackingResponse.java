package cps.DTO.ResponseBodies;

import java.time.LocalDate;

import cps.DomainLayer.models.Location;

public class TrackingResponse {
  public String status;
  public LocalDate eta;
  public Location destination;

  public TrackingResponse(String status, LocalDate eta, Location destination) {
    this.status = status;
    this.eta = eta;
    this.destination = destination;
  }
}