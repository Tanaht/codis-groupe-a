package ila.fr.codisintervention.models.model;

import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

/**
 * Class represents the request of vehicles by the intervenant
 * Created by aminesoumiaa on 26/04/18.
 */
@Getter
@Setter
public class Request {

    /** Id of the object Unit */
    private Integer id;

    /** Represent a vehicle */
    private Vehicle vehicle;

    /**
     * Instantiates a new Request.
     *
     * @param request the request
     */
    public Request(ila.fr.codisintervention.models.messages.Request request) {
        this.id = request.getId();
        this.vehicle = new Vehicle(request.getVehicle());
    }

    /**
     * Instantiate from model request
     * @param request
     */
    public void load(Request request){
        this.id = request.getId();
        this.vehicle = request.getVehicle();
    }
}
