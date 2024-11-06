package cps.models;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Mysqlcon {
  String url;
  String user;
  String password;

  public Mysqlcon() {
    Dotenv dotenv = Dotenv.load();
    String endpoint = dotenv.get("AWS_MYSQL_ENDPOINT");
    String port = dotenv.get("AWS_MYSQL_PORT");
    String dbName = dotenv.get("AWS_MYSQL_DB_NAME");
    this.url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
    this.user = dotenv.get("AWS_MYSQL_USER");
    this.password = dotenv.get("AWS_MYSQL_PASSWORD");
  }

  public void executeQuery(String query) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection(
          this.url, this.user, this.password);
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(query);
      System.out.println(resultSet);
      connection.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}