<<<<<<<< HEAD:backend/src/main/java/cps/DTO/ResponseBodies/BasicResponse.java
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
========
package cps.DomainLayer.models.ResponseBodies;

public class BasicResponse {
    protected String message;

    public BasicResponse(){
        this.message = "there was an error";
    }

    public BasicResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
>>>>>>>> contract:backend/src/main/java/cps/DomainLayer/models/ResponseBodies/BasicResponse.java
