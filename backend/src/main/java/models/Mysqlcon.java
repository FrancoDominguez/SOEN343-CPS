package models;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Mysqlcon {
  public void connect() {
    try {
      Dotenv dotenv = Dotenv.load();
      String endpoint = dotenv.get("AWS_MYSQL_ENDPOINT");
      String port = dotenv.get("AWS_MYSQL_PORT");
      String dbName = dotenv.get("AWS_MYSQL_DB_NAME");
      String url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;

      String user = dotenv.get("AWS_MYSQL_USER");
      String password = dotenv.get("AWS_MYSQL_PASSWORD");

      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(
          url, user, password);
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from Client");
      System.out.println(rs);
      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
