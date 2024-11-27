package cps.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;

import cps.DomainLayer.models.Station;
import cps.utils.Mysqlcon;

public class StationDAO {
  public Station fetchLocation(int stationId) {
    Station station = null;
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      String queryString = String.format("SELECT * FROM stations WHERE station_id = %d", stationId);
      con.formerExecuteQuery(queryString);
      ResultSet rs = con.getResultSet();
      if (rs.next()) {
        String name = rs.getString("name");
        String address = rs.getString("street_address");
        String postalCode = rs.getString("postal_code");
        String city = rs.getString("city");
        String country = rs.getString("street_address");
        station = new Station(stationId, name, address, postalCode, city, country);
      }
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return station;
  }

  public ArrayList<Station> fetchAllStations() {
    ArrayList<Station> stations = new ArrayList<Station>();
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      String queryString = String.format("SELECT * FROM stations");
      con.formerExecuteQuery(queryString);
      ResultSet rs = con.getResultSet();
      while (rs.next()) {
        int stationId = rs.getInt("station_id");
        String name = rs.getString("name");
        String address = rs.getString("street_address");
        String postalCode = rs.getString("postal_code");
        String city = rs.getString("city");
        String country = rs.getString("street_address");
        Station station = new Station(stationId, address , postalCode , city, country , name);
        stations.add(station);
      }
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return stations;
  }
}
