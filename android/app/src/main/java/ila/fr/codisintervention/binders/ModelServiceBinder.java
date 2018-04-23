package ila.fr.codisintervention.binders;

import android.os.Binder;

import java.util.List;

import ila.fr.codisintervention.models.InterventionChosen;
import ila.fr.codisintervention.models.messages.Code;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.Unit;
import ila.fr.codisintervention.models.messages.User;
import ila.fr.codisintervention.models.messages.Vehicle;

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
         * getter to have access to an intervention to the intervention actually chosen.
         * @return the selected intervention
         */
        InterventionChosen getSelectedIntervention();

        /**
         * list of interventions in progress
         * @return
         */
        List<Intervention> getInterventions();

        /**
         * TODO: is this method pertinent ? if yes, it has to throw a not found exception.
         * @param id identity of the intervention it refers to the value of {@see Intervention.id }
         * @return an instance of the intervention
         */
        Intervention getIntervention(int id);

        /**
         *
         * @return the list of Sinister Codes
         */
        List<Code> getCodes();

        /**
         *
         * @return the list of vehicles available.
         */
        List<Vehicle> getAvailableVehicle();

        /**
         *
         * @return the user connected
         */
        User getUser();

        /**
         * TODO: NotFoundException
         * A symbol in an intervention
         * @param id the id of the symbol
         * @return an instance of the symbol
         */
        Symbol getSymbol(int id);

        /**
         * TODO: NotFoundException
         * A unit in an intervention
         * @param id the id of the unit
         * @return an instance of the unit
         */
        Unit getUnit(int id);
    }
}
