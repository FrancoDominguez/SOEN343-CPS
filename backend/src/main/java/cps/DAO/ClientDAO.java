package cps.DAO;

import cps.DomainLayer.models.ClientModel;
import cps.utils.Mysqlcon;
import java.sql.*;

public class ClientDAO {

  public ClientModel fetchByEmail(String email) throws Exception {
    String query = "SELECT * FROM clients WHERE email = ?";

    Mysqlcon con = Mysqlcon.getInstance();
    con.connect();

    PreparedStatement statement = con.getConnection().prepareStatement(query);
    statement.setString(1, email);
    ResultSet rs = statement.executeQuery();

    ClientModel clientObj = null;
    if (rs.next()) {
      int clientId = rs.getInt("client_id");
      String firstname = rs.getString("firstname");
      String lastname = rs.getString("lastname");
      String password = rs.getString("password");
      clientObj = new ClientModel(clientId, firstname, lastname, email, password);
    }

    con.close();
    return clientObj;
  }

  public void insert(ClientModel clientObj) throws Exception {
    Mysqlcon con = Mysqlcon.getInstance();
    con.connect();
    String queryString = String.format(
        "INSERT INTO clients (firstname, lastname, email, password) VALUES ('%s', '%s', '%s', '%s')",
        clientObj.getFirstname(), clientObj.getLastname(), clientObj.getEmail(), clientObj.getPassword());
    con.formerExecuteUpdate(queryString);
    con.close();
  }

  public void update(ClientModel clientObj) throws Exception {
    Mysqlcon con = Mysqlcon.getInstance();
    con.connect();
    String queryString = String.format(
        "UPDATE clients SET firstname = '%s', lastname = '%s', email = '%s', password = '%s' WHERE client_id = '%d",
        clientObj.getFirstname(), clientObj.getLastname(), clientObj.getEmail(), clientObj.getPassword(),
        clientObj.getId());
    con.formerExecuteUpdate(queryString);
    con.close();
  }

  public void delete(int clientId) throws Exception {
    Mysqlcon con = Mysqlcon.getInstance();
    con.connect();
    String queryString = String.format("DELETE FROM clients WHERE client_id = ?", clientId);
    con.formerExecuteUpdate(queryString);
    con.close();
  }
}
