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
import ila.fr.codisintervention.exception.InterventionNotFoundException;
import ila.fr.codisintervention.exception.SymbolNotFoundException;
import ila.fr.codisintervention.exception.UnitNotFoundException;
import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.PathDrone;
import ila.fr.codisintervention.models.model.ApplicationModel;
import ila.fr.codisintervention.models.model.InterventionModel;
import ila.fr.codisintervention.models.model.Unit;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle;
import ila.fr.codisintervention.models.model.user.User;
import ila.fr.codisintervention.services.constants.ModelConstants;
import ila.fr.codisintervention.services.websocket.WebsocketService;

/**
 * Created by marzin on 28/03/18.
 * Android Service used to serve Model of the application to all Activities
 * This ModelService receive Explicit Intent send by {@link WebsocketService}
 * these intents are notifications from the remote websocket server about an update of the model.
 */

public class ModelService extends Service implements ModelServiceBinder.IMyServiceMethod {
    private static final String TAG = "ModelService";

    /**
     * Instance that contain the model
     */
    private ApplicationModel model;

    /**
     * Binder related to ModelService,
     * it's an instance of {@link ModelServiceBinder}
     */
    private IBinder binder;

    /**
     * Constructor to initialize the {@link ApplicationModel } instance
     */
    public ModelService() {
        this.model = new ApplicationModel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new ModelServiceBinder(this);

    }

    /**
     * This method is used to receive updates from {@link WebsocketService}
     * The lists of Intent Action possible is defined staticly in the {@link WebsocketService } class
     */
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


    /**
     * FIXME: Refactor me, perhaps with the pattern chain of responsibility ?
     * @see <a href="https://en.wikipedia.org/wiki/Chain-of-responsibility_pattern">Pattern that can be used according to me</a>
     *
     * Ugly method used to receive intent and perform action related to kind of intent received.
     * @param intent the explicit intent received from {@link WebsocketService}
     */
    public void updateTheModel(Intent intent) {
        Log.d(TAG, "Received intent : " + intent.getAction());
        if (intent.getAction() == null)
            return;

        switch (intent.getAction()) {
            case WebsocketService.CONNECT_TO_APPLICATION:
                InitializeApplication initializeApplication = intent.getParcelableExtra(WebsocketService.CONNECT_TO_APPLICATION);
                Log.d(TAG, "Connect to application with: " + initializeApplication.getInterventions().size() + " interventions");
                Gson gson = new GsonBuilder().create();
                Log.d(TAG, "RetrievedInitializeApplication: " + gson.toJson(initializeApplication));
                model = new ApplicationModel(initializeApplication);
                sendToEveryone(-1, ModelConstants.INITIALIZE_APPLICATION);
                break;
            case WebsocketService.INTERVENTION_CHOSEN:
                model.actualiseInterventionChoosen(intent.getParcelableExtra(WebsocketService.INTERVENTION_CHOSEN));
                break;
            case WebsocketService.DISCONNECT_TO_APPLICATION:
                model = new ApplicationModel();
                break;
            case WebsocketService.INTERVENTION_CREATED:
                Intervention intervention = intent.getParcelableExtra(WebsocketService.INTERVENTION_CREATED);
                model.getInterventions().add(new InterventionModel(intervention));
                sendToEveryone(intervention.getId(), ModelConstants.ADD_INTERVENTION);
                break;
            case WebsocketService.INTERVENTION_CLOSED:
                int id = intent.getIntExtra(WebsocketService.INTERVENTION_CLOSED, -1);
                try {
                    model.setInterventionClosedById(intent.getIntExtra(WebsocketService.INTERVENTION_CLOSED, -1));
                } catch (InterventionNotFoundException e) {
                    Log.e(TAG, "updateTheModel: try to closed a intervention who doesn't exist");
                    e.printStackTrace();
                }
                sendToEveryone(id, ModelConstants.ACTION_DELETE_INTERVENTION);
                break;
            case WebsocketService.INTERVENTION_SYMBOL_CREATED:
                Symbol symbolCreated = intent.getParcelableExtra(WebsocketService.INTERVENTION_SYMBOL_CREATED);
                model.getCurrentIntervention().getSymbols().add(symbolCreated);
                sendToEveryone(symbolCreated.getId(), ModelConstants.UPDATE_INTERVENTION_CREATE_SYMBOL);
                break;
            case WebsocketService.INTERVENTION_SYMBOL_UPDATED:
                Symbol symbolUpdated = intent.getParcelableExtra
                        (WebsocketService.INTERVENTION_SYMBOL_UPDATED);
                try {
                    model.getCurrentIntervention()
                            .updateSymbol(symbolUpdated);
                } catch (SymbolNotFoundException e) {
                    Log.e(TAG, "updateTheModel: try to update symbol who doesn't exist");
                    e.printStackTrace();
                }
                sendToEveryone(symbolUpdated.getId(), ModelConstants.UPDATE_INTERVENTION_UPDATE_SYMBOL);
                break;
            case WebsocketService.INTERVENTION_SYMBOL_DELETED:
                int idSymbol = intent.getIntExtra(WebsocketService.INTERVENTION_SYMBOL_DELETED, -1);
                try {
                    model.getCurrentIntervention().deleteSymbolById(idSymbol);
                } catch (SymbolNotFoundException e) {
                    Log.e(TAG, "updateTheModel: try to remove a symbol who doesn't exist");
                    e.printStackTrace();
                }
                sendToEveryone(idSymbol, ModelConstants.UPDATE_INTERVENTION_DELETE_SYMBOL);
                break;
            case WebsocketService.INTERVENTION_UNIT_CREATED:
                Unit unitCreated = intent.getParcelableExtra
                        (WebsocketService.INTERVENTION_UNIT_CREATED);
                model.getCurrentIntervention().getUnits().add(unitCreated);
                sendToEveryone(unitCreated.getId(), ModelConstants.UPDATE_INTERVENTION_CREATE_UNIT);
                break;
            case WebsocketService.INTERVENTION_UNIT_UPDATED:
                Unit unitUpdated = intent.getParcelableExtra
                        (WebsocketService.INTERVENTION_UNIT_UPDATED);
                try {
                    model.getCurrentIntervention().changeUnit(unitUpdated);
                } catch (UnitNotFoundException e) {
                    Log.e(TAG, "updateTheModel: try to update a unit who doesn't exist ");
                    e.printStackTrace();
                }
                sendToEveryone(unitUpdated.getId(), ModelConstants.UPDATE_INTERVENTION_UPDATE_UNIT);
                break;
            case WebsocketService.DEMANDE_ACCEPTED:
                break;
            case WebsocketService.DEMANDE_DENIED:
                break;
            case WebsocketService.DEMANDE_CREATED:
                break;
            case WebsocketService.DRONE_PING:
                break;
            case WebsocketService.DRONE_PHOTO:
                break;
            case WebsocketService.DRONE_PATH_RECEIVED:
                PathDrone pathDrone = intent.getParcelableExtra(WebsocketService.DRONE_PATH_RECEIVED);
                updateDronePathFromMessage(pathDrone);

                break;
            //Si l'action est mal définit
            default:
                Log.e(TAG, "Erreur d'action non reconnu pour la mise à jour du model");
        }
    }

    private void updateDronePathFromMessage(PathDrone pathDrone) {
        if(this.model.getCurrentIntervention() != null) {
            this.model.getCurrentIntervention().setPathDrone(new ila.fr.codisintervention.models.model.map_icon.drone.PathDrone(pathDrone));
            Intent intent = new Intent(ModelConstants.DRONE_PATH_ASSIGNED);
            intent.putExtra("pathDrone",pathDrone);
            deliverIntent(intent);
        } else {
            Log.e(TAG,"There is no curent intervention");
        }

    }

    /**
     * Used to broadcoast an intent to everyone
     * @param intent the intent to broadcoast
     */
    public void deliverIntent(Intent intent){
        Log.d(TAG, "Broadcoasted Intent: " + intent.getAction());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /**
     * FIXME: Every intent doesn't have the same extra signature, si it has to be removed -> it would be exported equally when we will refactor this class
     * Workaround to broadcoast an intent to everyone,
     * @param id the identifier to send in extra of the intent
     * @param intentAction the intent action name
     */
    public void sendToEveryone(int id, String intentAction){
        Intent intent = new Intent(intentAction);
        if(id!=-1) {
            // Adding some data
            intent.putExtra("id", id);
        }
        Log.d(TAG, "Broadcoast Intent: " + intent.getAction());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public List<InterventionModel> getInterventions() {
        return model.getInterventions();
    }

    @Override
    public void setCurrentIntervention(int id) throws InterventionNotFoundException {
        model.setCurrentIntervention(id);
    }

    @Override
    public InterventionModel getCurrentIntervention() {
        return model.getCurrentIntervention();
    }

    @Override
    public List<String> getSinisterCodes() {
        return model.getSinisterCodes();
    }

    @Override
    public List<String> getVehicleTypes() {
        return model.getVehicleTypes();
    }

    @Override
    public List<Vehicle> getAvailableVehicle() {
        return model.getVehicleAvailables();
    }

    @Override
    public User getUser() {
        return model.getUser();
    }
}
