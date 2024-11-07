package cps.services;

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
    String user = dotenv.get("AWS_MYSQL_USER");
    String password = dotenv.get("AWS_MYSQL_PASSWORD");
    this.url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
    this.user = user;
    this.password = password;
  }

  /**
   * Executes the given SQL query and returns the result set.
   * 
   * @param query the SQL query string to execute
   * @return the ResultSet of the executed query
   * @throws Exception if a database access error occurs or the query is invalid
   */
  public String executeQuery(String query) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection(
        this.url, this.user, this.password);

    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    StringBuilder stringBuilder = new StringBuilder();
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int columnsNumber = resultSetMetaData.getColumnCount();
    while (resultSet.next()) {
      for (int i = 1; i <= columnsNumber; i++) {
        stringBuilder.append(resultSet.getString(i));
        if (i != columnsNumber) {
          stringBuilder.append(" | ");
        }
      }
      stringBuilder.append("<br>");
    }
    connection.close();
    return stringBuilder.toString();
  }
}