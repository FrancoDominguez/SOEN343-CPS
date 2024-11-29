package cps.DAO;

import cps.DomainLayer.models.Review;
import cps.utils.Mysqlcon;

import java.sql.*;

public class ReviewDAO {

    // Fetch a review by its ID
    public Review fetchById(int reviewId) throws Exception {
        String query = "SELECT * FROM reviews WHERE review_id = ?";

        Mysqlcon con = Mysqlcon.getInstance();
        con.connect();

        PreparedStatement statement = con.getConnection().prepareStatement(query);
        statement.setInt(1, reviewId);
        ResultSet rs = statement.executeQuery();

        Review reviewObj = null;
        if (rs.next()) {
            int deliveryId = rs.getInt("delivery_id");
            int rating = rs.getInt("rating");
            String comment = rs.getString("comment");
            reviewObj = new Review(reviewId, deliveryId, rating, comment);
        }

        con.close();
        return reviewObj;
    }

    // Insert a new review
    public void insert(Review reviewObj) throws Exception {
        Mysqlcon con = Mysqlcon.getInstance();
        con.connect();

        String query = "INSERT INTO reviews (delivery_id, rating, comment) VALUES (?, ?, ?)";
        PreparedStatement statement = con.getConnection().prepareStatement(query);

        statement.setInt(1, reviewObj.getDeliveryId());
        statement.setInt(2, reviewObj.getRating());
        statement.setString(3, reviewObj.getComment());

        statement.executeUpdate();
        con.close();
    }


}
