package cps.DAO;

import cps.DomainLayer.models.Review;
import cps.utils.Mysqlcon;

import java.sql.*;

public class ReviewDAO {

    // Fetch a review by its ID
    public Review fetchById(int reviewId) throws Exception {
        String query = "SELECT * FROM reviews";

        Mysqlcon con = Mysqlcon.getInstance();
        con.connect();

        PreparedStatement statement = con.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        Review reviewObj = null;
        if (rs.next()) {
            String comment = rs.getString("comment");
            int rating = rs.getInt("rating");
            reviewObj = new Review(comment, rating);
        }

        con.close();
        return reviewObj;
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
