
package cps.DTO.ResponseBodies;

public class BasicResponse {
    protected String message;

    public BasicResponse() {
        this.message = "there was an error";
    }

    public BasicResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}