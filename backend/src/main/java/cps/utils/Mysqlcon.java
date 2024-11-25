package cps.utils;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Mysqlcon {
  // Declare the static instance variable
  private static volatile Mysqlcon instance;

  // Connection details (should be initialized statically)
  private static String url;
  private static String user;
  private static String password;

  // Instance variables for connection and result set
  private Connection connection;
  private ResultSet resultSet;

  // Static block to load environment variables and configure connection details
  static {
    Dotenv dotenv = Dotenv.load();
    String endpoint = dotenv.get("AWS_MYSQL_ENDPOINT");
    String port = dotenv.get("AWS_MYSQL_PORT");
    String dbName = dotenv.get("AWS_MYSQL_DB_NAME");
    user = dotenv.get("AWS_MYSQL_USER");
    password = dotenv.get("AWS_MYSQL_PASSWORD");
    url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
  }

  // Private constructor to prevent external instantiation
  private Mysqlcon() {
    // Constructor can be empty or used for other initializations
  }

  // Public static method to get the instance (Singleton pattern)
  public static Mysqlcon getInstance() {
    if (instance == null) {
      // Double-checked locking for thread safety
      synchronized (Mysqlcon.class) {
        if (instance == null) {
          instance = new Mysqlcon(); // Create the instance if not already created
        }
      }
    }
    return instance;
  }

  // Method to establish a connection to the database
  public void connect() throws Exception {
    if (this.connection == null || this.connection.isClosed()) {
      Class.forName("com.mysql.cj.jdbc.Driver");
      this.connection = DriverManager.getConnection(url, user, password);
    }
  }

  // Method to close the connection
  public void close() throws Exception {
    if (this.connection != null && !this.connection.isClosed()) {
      this.connection.close();
    }
  }

  // Method to execute a query and store the result in the resultSet
  public void executeQuery(String statementString) throws Exception {
    Statement statement = this.connection.createStatement();
    this.resultSet = statement.executeQuery(statementString);
  }

  // Method to execute an update (INSERT, UPDATE, DELETE)
  public String executeUpdate(String statementString) throws Exception {
    Statement statement = this.connection.createStatement();
    int rowsAffected = statement.executeUpdate(statementString);
    if (rowsAffected > 0) {
      return "Update successful. Rows affected: " + rowsAffected;
    } else {
      return "Update failed.";
    }
  }

  // Getter for resultSet (for reading data after query execution)
  public ResultSet getResultSet() {
    return this.resultSet;
  }

  public Connection getConnection() {
    return this.connection;
  }
}
