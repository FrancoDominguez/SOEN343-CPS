package cps;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

// import models.Mysqlcon;

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {
    System.out.println("Hello, World!");
    // Mysqlcon mysql = new Mysqlcon();
    // mysql.executeQuery("SQL QUERY HERE");
    SpringApplication.run(Driver.class, args);
  }
}
