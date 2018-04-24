package ila.fr.codisintervention.models.model;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.exception.InterventionNotFoundException;
import ila.fr.codisintervention.models.messages.Code;
import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.Type;
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

    private List<InterventionModel> interventions;
    private InterventionModel currentIntervention;

    private List<String> sinisterCodes;
    private List<String> vehicleTypes;


    public ApplicationModel(InitializeApplication init){
        sinisterCodes = new ArrayList<>();
        vehicleTypes = new ArrayList<>();
        interventions = new ArrayList<>();
        vehicleAvailables = new ArrayList<>();
        currentIntervention = null;

        for (Code code : init.getCodes()){
            sinisterCodes.add(code.getLabel());
        }
        for (Type vehicleType :  init.getTypes()){
            vehicleTypes.add(vehicleType.getLabel());
        }
        for (Intervention interv : init.getInterventions()){
            interventions.add(new InterventionModel(interv));
        }
        for (ila.fr.codisintervention.models.messages.Vehicle vehicle : init.getVehicles()){
            vehicleAvailables.add(new Vehicle(vehicle));
        }


    }

    public ApplicationModel() {

    }

    public void setCurrentIntervention(int idIntervention) throws InterventionNotFoundException {
        for (InterventionModel interv : interventions){
            if (interv.getId().equals(idIntervention)){
                currentIntervention = interv;
                return;
            }
        }

        throw new InterventionNotFoundException(idIntervention);
    }

    public InterventionModel getInterventionById(int id){
        for(InterventionModel intervention : interventions){
            if(intervention.getId().equals(id)){
                return intervention;
            }
        }
        return null;
    }

    public void setInterventionClosedById(int id) {
        for(InterventionModel intervention : interventions){
            if(intervention.getId().equals(id)){
                intervention.setOpened(false);
            }
        }
    }
}
