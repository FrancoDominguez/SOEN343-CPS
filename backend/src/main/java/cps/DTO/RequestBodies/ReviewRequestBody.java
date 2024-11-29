package cps.DTO.RequestBodies;

public class ReviewRequestBody {
    private int id;
    private int trackingId;
    private int rating; 
    private String comment; 

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public int getId() {
        return id;
    }

    public int getTrackingId(){
        return trackingId;
    }

}
