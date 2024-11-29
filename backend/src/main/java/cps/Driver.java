package cps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static cps.francoTest.testDAOs;

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {
    // testDAOs();
    SpringApplication.run(Driver.class, args);
  }
}
