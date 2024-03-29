package ila.fr.codisintervention.models.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.exception.InterventionNotFoundException;
import ila.fr.codisintervention.exception.RequestNotFoundException;
import ila.fr.codisintervention.exception.VehicleNotFoundException;
import ila.fr.codisintervention.models.messages.Code;
import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.Type;
import ila.fr.codisintervention.models.model.map_icon.drone.PathDrone;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;
import ila.fr.codisintervention.models.model.map_icon.vehicle.VehicleStatus;
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

    private static final String TAG = "ApplicationModel";

    private List<Vehicle> vehicles;

    private boolean droneAvailable = true;

    private User user;

    private PathDrone pathDrone;
    private List<InterventionModel> interventions;
    private InterventionModel currentIntervention;

    private List<String> sinisterCodes;
    private List<String> vehicleTypes;

    private List<Photo> photos;

    private List<Request> requests;

    /**
     * Instantiates a new Application model.
     */
    public ApplicationModel(){}

    /**
     * Instantiates a new Application model from initialize application message.
     *
     * @param init the init
     */
    public ApplicationModel(InitializeApplication init){
        sinisterCodes = new ArrayList<>();
        vehicleTypes = new ArrayList<>();
        interventions = new ArrayList<>();
        vehicles = new ArrayList<>();
        requests = new ArrayList<>();
        photos = new ArrayList<>();
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
            vehicles.add(new Vehicle(vehicle));
        }
        for(ila.fr.codisintervention.models.messages.Request req: init.getDemandes()){
            requests.add(new Request(req));
        }
        for(ila.fr.codisintervention.models.messages.Photo photo : init.getPhotos()){
            photos.add(new Photo((photo)));
        }

        user = new User(init.getUser());
    }

    /**
     * Sets current intervention from an id intervention contained in the interventions list.
     *
     * @param idIntervention the id intervention
     * @throws InterventionNotFoundException the intervention not found exception. Throw when the id doesn't exist in the list
     */
    public void setCurrentIntervention(int idIntervention) throws InterventionNotFoundException {
        for (InterventionModel interv : interventions){
            if (interv.getId().equals(idIntervention)){
                currentIntervention = interv;
                return;
            }
        }

        throw new InterventionNotFoundException(idIntervention);
    }

    /**
     * Delete intervention in the list intervention.
     *
     * @param idIntervention the id intervention
     * @throws InterventionNotFoundException the intervention not found exception
     */
    public void deleteIntervention(int idIntervention) throws InterventionNotFoundException {

        for (int i = 0; i < interventions.size(); i++) {
            if (interventions.get(i).getId().equals(idIntervention)){
                interventions.remove(i);
                Log.i(TAG, "deleteIntervention: intervention with id" + idIntervention + " removed");

                if (currentIntervention.getId().equals(idIntervention)){
                    currentIntervention = null;
                    Log.i(TAG, "deleteIntervention: current intervention is delete");
                }
                return;
            }
        }
        throw new InterventionNotFoundException(idIntervention);
    }

    /**
     * Actualise intervention current from intervention detailed intervention.
     *
     * @param intervention the intervention
     */
    public void actualiseInterventionChoosen(Intervention intervention){
        InterventionModel intervInList = null;
        for (InterventionModel interv : interventions){
            if (interv.getId().equals(intervention.getId())){
                intervInList = interv;
            }
        }
        if (intervInList != null){
            currentIntervention = intervInList;

            Log.d(TAG, "Set Current intervention to " + intervention.getId());

            List<Photo> photos = new ArrayList<>();
            for (ila.fr.codisintervention.models.messages.Photo photo : intervention.getPhotos()){
                photos.add(new Photo(photo));
            }
            currentIntervention.setPhotos(photos);

            List<Symbol> symbs  = new ArrayList<>();
            for (ila.fr.codisintervention.models.messages.Symbol symb : intervention.getSymbols()){
                symbs.add(new Symbol(symb));
                Log.d(TAG, "Add a new Symbol to intervention chosen");
            }
            currentIntervention.setSymbols(symbs);

            List<Unit> units = new ArrayList<>();
            for (ila.fr.codisintervention.models.messages.Unit uni : intervention.getUnits()){
                Log.d(TAG, "Add a new Unit to intervention chosen");
                units.add(new Unit(uni));
            }
            currentIntervention.setUnits(units);

            if(intervention.getPathDrone() != null)
                currentIntervention.setPathDrone(new PathDrone(intervention.getPathDrone()));

            Log.d(TAG, "Set Location on current intervention to " + intervention.getLocation());
            currentIntervention.setLocation(intervInList.getLocation());
            currentIntervention.setSinisterCode(intervInList.getSinisterCode());
            currentIntervention.setAddress(intervInList.getAddress());
            currentIntervention.setDate(intervInList.getDate());
            currentIntervention.setOpened(intervInList.isOpened());
        }
    }

    /**
     * Close an intervention.
     *
     * @param id the id
     * @throws InterventionNotFoundException the intervention not found exception
     */
    public void setInterventionClosedById(int id) throws InterventionNotFoundException {
        for(InterventionModel intervention : interventions){
            if(intervention.getId().equals(id)){
                intervention.setOpened(false);
                return;
            }
        }
        throw new InterventionNotFoundException(id);
    }

    /**
     * Gets intervention by id.
     *
     * @param id the id
     * @return the intervention by id
     * @throws InterventionNotFoundException the intervention not found exception
     */
    public InterventionModel getInterventionById(int id) throws InterventionNotFoundException {
        for(InterventionModel intervention : interventions){
            if(intervention.getId().equals(id)){
                return intervention;
            }
        }
        throw new InterventionNotFoundException(id);
    }


    public Request getRequestById(int id) throws RequestNotFoundException {
        for(Request request: requests){
            if(request.getId().equals(id)){
                return request;
            }
        }
        throw new RequestNotFoundException(id);
    }

    /**
     * @return The list of available vehicles
     */
    public List<Vehicle> getAvailableVehicles() {

        List<Vehicle> availableVehicles = new ArrayList<>();

        for(Vehicle vehicle : getVehicles())
            if(VehicleStatus.AVAILABLE.equals(vehicle.getStatus()))
                availableVehicles.add(vehicle);


        return availableVehicles;
    }

    /**
     * Return vehicles according to a type
     * @param type the type to look for
     * @return a list of vehicle instance
     */
    public List<Vehicle> getAvailableVehiclesByType(String type) {

        List<Vehicle> availableVehicleFilteredByTypes = new ArrayList<>();

        for(Vehicle vehicle : getVehicles())
            if(VehicleStatus.AVAILABLE.equals(vehicle.getStatus()) && type.equals(vehicle.getType()))
                availableVehicleFilteredByTypes.add(vehicle);


        return availableVehicleFilteredByTypes;
    }


    /**
     * Return a vehicle given it's label
     * @param label the labe
     * @return
     * @throws ila.fr.codisintervention.exception.VehicleNotFoundException if no vehicle can be found
     */
    public Vehicle getVehicleByLabel(String label) throws VehicleNotFoundException {
        for(Vehicle vehicle : getVehicles())
            if(label.equals(vehicle.getLabel()))
                return vehicle;

        throw new VehicleNotFoundException("Unable to find a vehicle with the following label: '" + label + "'");
    }
}
