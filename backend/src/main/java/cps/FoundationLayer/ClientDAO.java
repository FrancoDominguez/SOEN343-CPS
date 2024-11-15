package cps.FoundationLayer;

import cps.models.Client;
import cps.utils.Mysqlcon;
import java.sql.*;

public class ClientDAO {

  // Get a user by email
  public Client getUserByEmail(String email) throws Exception {
    // Create a query string
    String query = "SELECT * FROM clients WHERE email = ?";
    
    // Get the singleton Mysqlcon instance
    Mysqlcon mysqlConnection = Mysqlcon.getInstance();
    mysqlConnection.connect();

    // Prepare the statement and execute the query
    PreparedStatement statement = mysqlConnection.getConnection().prepareStatement(query);
    statement.setString(1, email);
    ResultSet resultSet = statement.executeQuery();

    Client user = null;
    // If a user is found, create a User object
    if (resultSet.next()) {
      int userId = resultSet.getInt("client_id");
      String firstname = resultSet.getString("firstname");
      String lastname = resultSet.getString("lastname");
      String password = resultSet.getString("password");

      user = new Client(userId, firstname, lastname, email, password);
    }

    mysqlConnection.close(); // Close the connection
    return user; // Return the user (or null if not found)
  }

  // Create a new user in the database
  public boolean createUser(Client user) throws Exception {
    // Create a query string for inserting a new user
    String query = "INSERT INTO clients (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";

    // Get the singleton Mysqlcon instance
    Mysqlcon mysqlConnection = Mysqlcon.getInstance();
    mysqlConnection.connect();

    // Prepare the statement and execute the update
    PreparedStatement statement = mysqlConnection.getConnection().prepareStatement(query);
    statement.setString(1, user.getFirstname());
    statement.setString(2, user.getLastname());
    statement.setString(3, user.getEmail());
    statement.setString(4, user.getPassword());

    int rowsAffected = statement.executeUpdate();

    mysqlConnection.close(); // Close the connection

    return rowsAffected > 0; // Return true if the user was created
  }

  // Update an existing user
  public boolean updateUser(Client user) throws Exception {
    // Create a query string for updating a user
    String query = "UPDATE clients SET firstname = ?, lastname = ?, email = ?, password = ? WHERE client_id = ?";

    // Get the singleton Mysqlcon instance
    Mysqlcon mysqlConnection = Mysqlcon.getInstance();
    mysqlConnection.connect();

    // Prepare the statement and execute the update
    PreparedStatement statement = mysqlConnection.getConnection().prepareStatement(query);
    statement.setString(1, user.getFirstname());
    statement.setString(2, user.getLastname());
    statement.setString(3, user.getEmail());
    statement.setString(4, user.getPassword());
    statement.setInt(5, user.getId());

    int rowsAffected = statement.executeUpdate();

    mysqlConnection.close(); // Close the connection

    return rowsAffected > 0; // Return true if the user was updated
  }

  // Delete a user by ID
  public boolean deleteUser(int userId) throws Exception {
    // Create a query string for deleting a user
    String query = "DELETE FROM clients WHERE client_id = ?";

    // Get the singleton Mysqlcon instance
    Mysqlcon mysqlConnection = Mysqlcon.getInstance();
    mysqlConnection.connect();

    // Prepare the statement and execute the delete
    PreparedStatement statement = mysqlConnection.getConnection().prepareStatement(query);
    statement.setInt(1, userId);

    int rowsAffected = statement.executeUpdate();

    mysqlConnection.close(); // Close the connection

    return rowsAffected > 0; // Return true if the user was deleted
  }

  // Optional: You could also include other helper methods, e.g., for validating email, etc.
}
