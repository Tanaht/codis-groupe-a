package ila.fr.codisintervention.models.model.map_icon.symbol;


import lombok.Getter;
import lombok.Setter;

/**
 * The symbol payload.
 */
@Getter
@Setter
public class Payload {

    /** The identifier of the payload */
    private String identifier;

    /** The details of the payload */
    private String details;

    public void load(Payload payload){
        identifier = payload.getIdentifier();
        details = payload.getDetails();
    }

}
