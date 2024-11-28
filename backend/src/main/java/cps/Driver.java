package cps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static cps.francoTest.testDAOs;
import static cps.francoTest.clearTable;

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {

    // testDAOs();
    // System.out.println("Clearing all tables\n");
    // clearTable("contracts");
    // clearTable("locations");
    // clearTable("parcels");
    // System.out.println("All tables are now clear\n");

    SpringApplication.run(Driver.class, args);
  }
}
