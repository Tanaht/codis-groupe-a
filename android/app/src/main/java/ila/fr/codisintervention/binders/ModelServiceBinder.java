package ila.fr.codisintervention.binders;

import android.os.Binder;

import java.util.List;

import ila.fr.codisintervention.exception.InterventionNotFoundException;
import ila.fr.codisintervention.exception.RequestNotFoundException;
import ila.fr.codisintervention.exception.VehicleNotFoundException;
import ila.fr.codisintervention.models.model.InterventionModel;
import ila.fr.codisintervention.models.model.Request;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;
import ila.fr.codisintervention.models.model.user.User;

/**
 * Created by tanaky on 29/03/18.
 * This class is a binder for the {@link ila.fr.codisintervention.services.model.ModelService}
 * it allow other android components like activities to have access to the {@link ila.fr.codisintervention.services.model.ModelService} instance
 */
public class ModelServiceBinder extends Binder {

    /**
     * Instance of ModelService class, it define an interface ModelServiceBinder.IMyServiceMethod to allow request through a predefined API.
     */
    private IMyServiceMethod service;


    /**
     * Instantiates a new Model service binder.
     *
     * @param service the model service instance
     */
    public ModelServiceBinder(IMyServiceMethod service) {
        super();
        this.service = service;
    }

    /** @return the instance of ModelService */
    public IMyServiceMethod getService(){
        return service;
    }

    /**
     * this nested interface define all the API of the ModelService, and this method are known from android components thanks to the binder.
     * FIXME: Currently the ModelService define no concrete model, it's a patchwork of JsonObject Class put it together like that.
     * TODO: Interfaces that define the API has to be made in order to have a robust model
     */
    public interface IMyServiceMethod {

        /**
         * list of interventions in progress
         * @return
         */
        List<InterventionModel> getInterventions();

        InterventionModel getInterventionById(int id) throws InterventionNotFoundException;

        /**
         * TODO: is this method pertinent ? if yes, it has to throw a not found exception.
         * @param id identity of the intervention it refers to the value of {@see Intervention.id }
         * @return an instance of the intervention
         */
        void setCurrentIntervention(int id) throws InterventionNotFoundException;

        /**
         * get intervention selected from user
         * @return Current intervention selected by the user
         */
        InterventionModel getCurrentIntervention();

        /**
         *
         * @return the list of Sinister Codes
         */
        List<String> getSinisterCodes();
        List<String> getVehicleTypes();

        /**
         *
         * @return the list of vehicles available.
         */
        List<Vehicle> getAvailableVehicle();

        /**
         *
         * @return the list of vehicles
         */
        List<Vehicle> getVehicles();

        /**
         * Return vehicles according to a type
         * @param type the type to look for
         * @return a list of vehicle instance
         */
        List<Vehicle> getAvailableVehiclesByType(String type);

        /**
         * Return a vehicle given it's label
         * @param label the labe
         * @return
         *
         */
        Vehicle getVehicleByLabel(String label) throws VehicleNotFoundException;

        /**
         *
         * @return the list of vehicle requests
         */
        List<Request> getRequests();

        /**
         * @param id the id of the request
         * @return the request
         * @throws RequestNotFoundException
         */
        Request getRequestById(int id) throws RequestNotFoundException;

        /**
         *
         * @return the user connected
         */
        User getUser();

    }
}
