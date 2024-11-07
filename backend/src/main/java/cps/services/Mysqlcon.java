package cps.services;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;
import cps.exceptions.EnvFileRequiredException;

public class Mysqlcon {
  String url;
  String user;
  String password;

  public Mysqlcon() {
    Dotenv dotenv = Dotenv.load();
    String endpoint = dotenv.get("AWS_MYSQL_ENDPOINT");
    String port = dotenv.get("AWS_MYSQL_PORT");
    String dbName = dotenv.get("AWS_MYSQL_DB_NAME");
    String user = dotenv.get("AWS_MYSQL_USER");
    String password = dotenv.get("AWS_MYSQL_PASSWORD");

    this.url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
    this.user = user;
    this.password = password;
  }

  public ResultSet executeQuery(String query) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection(
        this.url, this.user, this.password);

    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int columnsNumber = resultSetMetaData.getColumnCount();
    while (resultSet.next()) {
      for (int i = 1; i <= columnsNumber; i++) {
        System.out.print(resultSet.getString(i) + " ");
      }
      System.out.println();
    }
    connection.close();
    return resultSet;
  }
}