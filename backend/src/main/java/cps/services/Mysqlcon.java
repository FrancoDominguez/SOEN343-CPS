package cps.services;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Mysqlcon {
  private static String url;
  private static String user;
  private static String password;
  private Connection connection;
  private ResultSet resultSet;

  static {
    Dotenv dotenv = Dotenv.load();
    String endpoint = dotenv.get("AWS_MYSQL_ENDPOINT");
    String port = dotenv.get("AWS_MYSQL_PORT");
    String dbName = dotenv.get("AWS_MYSQL_DB_NAME");
    user = dotenv.get("AWS_MYSQL_USER");
    password = dotenv.get("AWS_MYSQL_PASSWORD");
    url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
  }

  public Mysqlcon() {
    // Constructor can be empty or used for other initializations
  }

  public void connect() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    this.connection = DriverManager.getConnection(
        url, user, password);
  }

  public void close() throws Exception {
    this.connection.close();
  }

  public void executeQuery(String statementString) throws Exception {
    Statement statement = this.connection.createStatement();
    this.resultSet = statement.executeQuery(statementString);
  }

  public String executeUpdate(String statementString) throws Exception {
    Statement statement = this.connection.createStatement();
    int rowsAffected = statement.executeUpdate(statementString);
    if (rowsAffected > 0) {
      return "Update successful. Rows affected: " + rowsAffected;
    } else {
      return "Update failed.";
    }
  }

  public ResultSet getResultSet() {
    return this.resultSet;
  }
}