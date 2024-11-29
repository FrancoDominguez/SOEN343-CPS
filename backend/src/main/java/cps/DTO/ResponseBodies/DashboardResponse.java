package cps.DTO.ResponseBodies;

import java.util.List;

public class DashboardResponse extends BasicResponse{
    private List contracts;
    private List deliveries;
    public DashboardResponse(String message, List contracts, List deliveries){
        this.message = message;
        this.contracts = contracts;
        this.deliveries = deliveries;
    }
    
}
