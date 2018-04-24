package ila.fr.codisintervention.models.model;

import java.util.List;

import ila.fr.codisintervention.exception.InterventionNotFoundException;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;
import ila.fr.codisintervention.models.model.user.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by marzin on 28/03/18.
 * The ApplicationModel is work is to contain the Model of the Interventions and Vehicles Requests
 * and to keep the integrity of the datas contained
 * <p>
 */
@Getter
@Setter
public class ApplicationModel {


    private List<Vehicle> vehicleAvailables;

    private boolean droneAvailable = true;

    private User user;

    private List<InterventionModel> listIntervention;
    private InterventionModel currentIntervention;

    private List<String> sinisterCodes;
    private List<String> vehicleTypes;


    public void setCurrentIntervention(int idIntervention) throws InterventionNotFoundException {
        for (InterventionModel interv : listIntervention){
            if (interv.getId().equals(idIntervention)){
                currentIntervention = interv;
                return;
            }
        }

        throw new InterventionNotFoundException(idIntervention);
    }

}
