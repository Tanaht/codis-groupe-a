package ila.fr.codisintervention.services.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.List;

import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.models.BigModel;
import ila.fr.codisintervention.models.messages.Code;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.Unit;
import ila.fr.codisintervention.models.messages.Vehicle;
import ila.fr.codisintervention.services.constants.ModelConstants;

/**
 * Created by marzin on 28/03/18.
 */

public class ModelService extends Service implements ModelServiceBinder.IMyServiceMethod {
    private final static String TAG = "ModelService";

    private BigModel model;
    private IBinder binder;


    //Méthode pour metre à jour le model
    public void updateTheModel (Intent intent) {
        switch (intent.getAction()){
            //Pour les messages
            case "CONNECT_TO_APPLICATION" :
                model.setMessageInitialize(intent.getParcelableExtra("CONNECT_TO_APPLICATION"));
                break;
            //Pour le current Intervention
            case "INTERVENTION_CHOSEN" :
                model.setCurrentIntervention(intent.getParcelableExtra("INTERVENTION_CHOSEN"));
                break;
            case "DISCONNECT_TO_APPLICATION" :
                model = new BigModel();
                 break;
            case "INTERVENTION_CREATED" :
                Intervention intervention = intent.getParcelableExtra("INTERVENTION_CREATED");
                model.getMessageInitialize().getInterventions().add(intervention);
                Intent intentCreatedIntervention = new Intent(ModelConstants.ACTION_ADD_INTERVENTION);
                // Adding some data
                intentCreatedIntervention.putExtra("id",intervention.getId());
                LocalBroadcastManager.getInstance(this).sendBroadcast(intentCreatedIntervention);
                break;
            case "INTERVENTION_CLOSED" :
                int id = intent.getIntExtra("INTERVENTION_CLOSED",-1);
                model.getMessageInitialize().setInterventionClosedById(intent.getIntExtra("INTERVENTION_CLOSED",-1));
                Intent intentClosedIntervention = new Intent(ModelConstants.ACTION_DELETE_INTERVENTION);
                // Adding some data
                intentClosedIntervention.putExtra("id",id);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intentClosedIntervention);
                break;
            case "INTERVENTION_SYMBOL_CREATED" :
               break;
            case "INTERVENTION_SYMBOL_UPDATED" :
                break;
            case "INTERVENTION_SYMBOL_DELETED" :
                break;
            case "INTERVENTION_UNIT_CREATED" :
                break;
            case "INTERVENTION_UNIT_UPDATED" :
                break;
            case "DEMANDE_ACCEPTED" :
                break;
            case "DEMANDE_DENIED" :
                break;
            case "DEMANDE_CREATED" :
                break;
            case "DRONE_PING" :
                break;
            case "DRONE_PHOTO" :
                break;
            //Si l'action est mal définit
            default :
                Log.e("Erreur", "Erreur d'action non reconnu pour la mise à jour du model");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "OnCreate ModelService");
        binder = new ModelServiceBinder(this);

    }

    public BigModel getModel() {
        return model;
    }

    @Override
    public Intervention getSelectedIntervention() {
        return null;
    }

    @Override
    public List<Intervention> getInterventions() {
        return null;
    }

    @Override
    public List<Code> getCodes() {
        return null;
    }

    @Override
    public List<Vehicle> getAvailableVehicle() {
        return null;
    }

    @Override
    public Symbol getSymbol(int id) {
        return null;
    }

    @Override
    public Unit getUnit(int id) {
        return null;
    }
}
