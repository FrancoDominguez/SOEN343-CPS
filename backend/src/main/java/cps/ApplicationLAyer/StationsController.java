package cps.ApplicationLayer;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cps.DomainLayer.ClientService;
import cps.DomainLayer.models.Station;

@RestController
public class StationsController {

  @GetMapping("/stations")
  public ArrayList<Station> getStations() {
    ClientService clientService = new ClientService();
    return clientService.getAllStations();
  }
}