import models.Mysqlcon;

public class Driver {
  public static void main(String[] args) {
    System.out.println("Hello, World!");
    Mysqlcon mysql = new Mysqlcon();
    mysql.executeQuery("sum sql shid");
  }
}
