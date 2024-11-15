package cps.models;

public class Client {
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
    
    public String getPassword() {
        return this.password;
    }

    // Constructor for a new user
    public Client(String firstname, String lastname, String email, String password) {
        this.id = -1;  // -1 indicates a new user
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    // Constructor for an existing user (retrieved from the database)
    public Client(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

}