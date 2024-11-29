package cps.DomainLayer.models;

public class Review {
     private String comment; 
     private int rating; 
   
     
    public Review(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "Rating=" + rating +
                ", Comment='" + comment + '\'' +
                '}';
    }
}
