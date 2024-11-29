package cps.DomainLayer.models;

public class Review {
    private int id;
    private int trackingId;
    private int rating; 
    private String comment; 
     
    public Review(int id, int trackingId, int rating, String comment) {
        this.id = id;
        this.trackingId = trackingId;
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

    public int getId() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public int getTrackingId(){
        return trackingId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "Rating=" + rating +
                ", Comment='" + comment + '\'' +
                ", Tracking ID='" + trackingId + '\'' +
                '}';
    }
}
