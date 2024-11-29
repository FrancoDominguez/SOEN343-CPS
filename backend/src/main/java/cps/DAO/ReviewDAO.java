package cps.DAO;

import cps.DomainLayer.models.Review;
import cps.utils.Mysqlcon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    
    public ArrayList<Review> fetchAll() throws Exception {
        String query = "SELECT * FROM reviews";

        Mysqlcon con = Mysqlcon.getInstance();
        con.connect();

        PreparedStatement statement = con.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        ArrayList<Review> reviews = new ArrayList<>();
        while (rs.next()) {
            String comment = rs.getString("comment");
            int rating = rs.getInt("rating");
            Review reviewObj = new Review(comment, rating);
            reviews.add(reviewObj);
        }

        con.close();
        return reviews;
    }

    public void insert(Review review) throws Exception {
        String query = "INSERT INTO reviews (comment, rating) VALUES (?, ?)";
        Mysqlcon con = Mysqlcon.getInstance();
        con.connect();
    
        PreparedStatement statement = con.getConnection().prepareStatement(query);
        statement.setString(1, review.getComment());
        statement.setInt(2, review.getRating());
        statement.executeUpdate();
    
        con.close();
    }
    


}
