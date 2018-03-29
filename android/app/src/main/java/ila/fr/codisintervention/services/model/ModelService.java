package ila.fr.codisintervention.services.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.models.BigModel;
import ila.fr.codisintervention.models.InterventionChosen;
import ila.fr.codisintervention.models.messages.Code;
import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.Unit;
import ila.fr.codisintervention.models.messages.User;
import ila.fr.codisintervention.models.messages.Vehicle;
import ila.fr.codisintervention.services.constants.ModelConstants;
import ila.fr.codisintervention.services.websocket.WebsocketService;

/**
 * Created by marzin on 28/03/18.
 */

public class ModelService extends Service implements ModelServiceBinder.IMyServiceMethod {
    private final static String TAG = "ModelService";

    private BigModel model;
    private IBinder binder;

    public ModelService() {
        this.model = new BigModel();
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "START COMMAND by " + intent);

        try {
            updateTheModel(intent);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return super.onStartCommand(intent, flags, startId);
    }



    //Méthode pour metre à jour le model
    public void updateTheModel (Intent intent) {
        if(intent.getAction() == null)
            return;

        switch (intent.getAction()){
            //Pour les messages
            case  WebsocketService.CONNECT_TO_APPLICATION:
                InitializeApplication initializeApplication = intent.getParcelableExtra(WebsocketService.CONNECT_TO_APPLICATION);
                Log.d(TAG, "Connect to application with: "+ initializeApplication.getInterventions().size() + " interventions");
                Gson gson = new GsonBuilder().create();

                Log.d(TAG, "RetrievedInitializeApplication: " +  gson.toJson(initializeApplication));

                model.setMessageInitialize(initializeApplication);

                Intent initializeApplicationIntent = new Intent(ModelConstants.ACTION_INITIALIZE_APPLICATION);


                Log.d(TAG, "Broadcoast Intent: " + initializeApplicationIntent.getAction());
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(initializeApplicationIntent);

                break;
            //Pour le current Intervention
            case WebsocketService.INTERVENTION_CHOSEN :
                model.setCurrentIntervention(InterventionChosen.createByIntervention(intent.getParcelableExtra("INTERVENTION_CHOSEN")));
                break;
            case WebsocketService.DISCONNECT_TO_APPLICATION :
                model = new BigModel();
                 break;
            case WebsocketService.INTERVENTION_CREATED :
                Intervention intervention = intent.getParcelableExtra("INTERVENTION_CREATED");
                model.getMessageInitialize().getInterventions().add(intervention);


                Intent intentCreatedIntervention = new Intent(ModelConstants.ACTION_ADD_INTERVENTION);
                // Adding some data
                intentCreatedIntervention.putExtra("id",intervention.getId());

                Log.d(TAG, "Broadcoast Intent: " + intentCreatedIntervention.getAction());

                LocalBroadcastManager.getInstance(this).sendBroadcast(intentCreatedIntervention);
                break;
            case WebsocketService.INTERVENTION_CLOSED :
                int id = intent.getIntExtra(WebsocketService.INTERVENTION_CLOSED,-1);
                model.getMessageInitialize().setInterventionClosedById(intent.getIntExtra("INTERVENTION_CLOSED",-1));
                Intent intentClosedIntervention = new Intent(ModelConstants.ACTION_DELETE_INTERVENTION);
                // Adding some data
                intentClosedIntervention.putExtra("id",id);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intentClosedIntervention);
                break;
            case WebsocketService.INTERVENTION_SYMBOL_CREATED :
                Symbol symbolCreated = intent.getParcelableExtra(WebsocketService.INTERVENTION_SYMBOL_CREATED);
                model.getCurrentIntervention().getSymbols().add(symbolCreated);

                Intent intentCreatedSymbol = new Intent(ModelConstants.ACTION_UPDATE_INTERVENTION_CREATE_SYMBOL);
                // Adding some data
                intentCreatedSymbol.putExtra("id",symbolCreated.getId());

                Log.d(TAG, "Broadcoast Intent: " + intentCreatedSymbol.getAction());

                LocalBroadcastManager.getInstance(this).sendBroadcast(intentCreatedSymbol);

                break;
            case WebsocketService.INTERVENTION_SYMBOL_UPDATED :
                Symbol symbolUpdated = intent.getParcelableExtra
                        (WebsocketService.INTERVENTION_SYMBOL_UPDATED);
                model.getCurrentIntervention()
                        .changeSymbol(symbolUpdated);
                Intent intentUpdatedSymbol = new Intent(ModelConstants.ACTION_UPDATE_INTERVENTION_UPDATE_SYMBOL);
                // Adding some data
                intentUpdatedSymbol.putExtra("id",symbolUpdated.getId());

                Log.d(TAG, "Broadcoast Intent: " + intentUpdatedSymbol.getAction());

                LocalBroadcastManager.getInstance(this).sendBroadcast(intentUpdatedSymbol);

                break;
            case WebsocketService.INTERVENTION_SYMBOL_DELETED :
                int idSymbol = intent.getIntExtra(WebsocketService.INTERVENTION_SYMBOL_DELETED,-1);
                model.getCurrentIntervention().deleteSymbolById(idSymbol);
                Intent intentDeleteSymbol = new Intent(ModelConstants.ACTION_UPDATE_INTERVENTION_DELETE_SYMBOL);
                // Adding some data
                intentDeleteSymbol.putExtra("id",idSymbol);

                Log.d(TAG, "Broadcoast Intent: " + intentDeleteSymbol.getAction());

                LocalBroadcastManager.getInstance(this).sendBroadcast(intentDeleteSymbol);

                break;
            case WebsocketService.INTERVENTION_UNIT_CREATED :
                Unit unitCreated = intent.getParcelableExtra
                        (WebsocketService.INTERVENTION_UNIT_CREATED);
                model.getCurrentIntervention().getUnits().add(unitCreated);

                Intent intentCreatedUnit = new Intent(ModelConstants.ACTION_UPDATE_INTERVENTION_CREATE_UTIL);
                // Adding some data
                intentCreatedUnit.putExtra("id",unitCreated.getId());

                Log.d(TAG, "Broadcoast Intent: " + intentCreatedUnit.getAction());

                LocalBroadcastManager.getInstance(this).sendBroadcast(intentCreatedUnit);

                break;
            case WebsocketService.INTERVENTION_UNIT_UPDATED :
               Unit unitUpdated = intent.getParcelableExtra
                       (WebsocketService.INTERVENTION_UNIT_UPDATED);
                model.getCurrentIntervention().changeUnit(unitUpdated);

                Intent intentUpdatedUnit = new Intent(ModelConstants.ACTION_UPDATE_INTERVENTION_UPDATE_UTIL);
                // Adding some data
                intentUpdatedUnit.putExtra("id",unitUpdated.getId());

                Log.d(TAG, "Broadcoast Intent: " + intentUpdatedUnit.getAction());

                LocalBroadcastManager.getInstance(this).sendBroadcast(intentUpdatedUnit);

                break;
            case WebsocketService.DEMANDE_ACCEPTED :
                break;
            case WebsocketService.DEMANDE_DENIED :
                break;
            case WebsocketService.DEMANDE_CREATED :
                break;
            case WebsocketService.DRONE_PING:
                break;
            case WebsocketService.DRONE_PHOTO :
                break;
            //Si l'action est mal définit
            default :
                Log.e("Erreur", "Erreur d'action non reconnu pour la mise à jour du model");
        }
    }

    public BigModel getModel() {
        return model;
    }

    @Override
    public InterventionChosen getSelectedIntervention() {
        return model.getCurrentIntervention();
    }

    @Override
    public List<Intervention> getInterventions() {
        return model.getMessageInitialize().getInterventions();
    }

    @Override
    public List<Code> getCodes() {
        return model.getMessageInitialize().getCodes();
    }

    @Override
    public List<Vehicle> getAvailableVehicle() {
        return model.getMessageInitialize().getVehicles();
    }

    @Override
    public User getUser() {
        return model.getUser();
    }

    @Override
    public Symbol getSymbol(int id) {
        return model.getCurrentIntervention().getSymbolId(id);
    }

    @Override
    public Unit getUnit(int id) {
        return model.getCurrentIntervention().getUnitById(id);
    }
}
