package cps.DomainLayer.models;

public class Review {
    private int id;
    private int deliveryId;
    private int rating; 
    private String comment; 
     
    public Review(int id, int deliveryId, int rating, String comment) {
        this.id = id;
        this.deliveryId = deliveryId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public int getDeliveryId(){
        return deliveryId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "Rating=" + rating +
                ", Comment='" + comment + '\'' +
                ", Delivery ID='" + deliveryId + '\'' +
                '}';
    }
}
