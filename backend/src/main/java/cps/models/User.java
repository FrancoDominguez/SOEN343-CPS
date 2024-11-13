package cps.models;

import cps.services.Mysqlcon;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public int getId() {
        return this.id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getEmail() {
        return this.email;
    }

    // Constructor for a new user
    public User(String firstname, String lastname, String email, String password) {
        this.id = -1;  // -1 indicates a new user
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    // Constructor for an existing user (retrieved from the database)
    public User(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    // Save or update user in the database
    public void save() {
        try {
            Mysqlcon mysqlcon = new Mysqlcon();
            mysqlcon.connect();
            if (this.id == -1) {
                // Insert new user
                String insertSQL = String.format(
                    "INSERT INTO clients (firstname, lastname, email, password) VALUES ('%s', '%s', '%s', '%s')",
                    this.firstname, this.lastname, this.email, this.password);
                String result = mysqlcon.executeUpdate(insertSQL);

                
            } else {
                // Update existing user
                String updateSQL = String.format(
                    "UPDATE clients SET firstname='%s', lastname='%s', email='%s', password='%s' WHERE id=%d",
                    this.firstname, this.lastname, this.email, this.password, this.id);
                String result = mysqlcon.executeUpdate(updateSQL);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}