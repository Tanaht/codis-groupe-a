package ila.fr.codisintervention.models.model;

import java.util.List;

import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.model.map_icon.drone.PathDrone;
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

    private InitializeApplication messageInitialize;

    private InterventionModel currentIntervention;

    private List<InterventionModel> listIntervention;

    private List<PathDrone> listPathDrone;

    private List<Unit> listUnit;

    private boolean droneAvailable;

}
