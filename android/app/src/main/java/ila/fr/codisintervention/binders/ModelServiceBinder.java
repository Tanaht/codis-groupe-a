package ila.fr.codisintervention.binders;

import android.os.Binder;

import java.util.List;

import ila.fr.codisintervention.models.messages.Code;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.Unit;
import ila.fr.codisintervention.models.messages.Vehicle;

/**
 * Created by tanaky on 29/03/18.
 */

public class ModelServiceBinder extends Binder {

    private IMyServiceMethod service;

    public ModelServiceBinder(IMyServiceMethod service) {
        super();
        this.service = service;
    }

    /** @return l'instance du service */
    public IMyServiceMethod getService(){
        return service;
    }

    /** les méthodes de cette interface seront accessibles par l'activité */
    public interface IMyServiceMethod {
        Intervention getSelectedIntervention();
        List<Intervention> getInterventions();
        List<Code> getCodes();
        List<Vehicle> getAvailableVehicle();

        Symbol getSymbol(int id);
        Unit getUnit(int id);
    }
}
