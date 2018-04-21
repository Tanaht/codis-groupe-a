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
 * Android Service used to serve Model of the application to all Activities
 * This ModelService receive Explicit Intent send by {@link WebsocketService}
 * these intents are notifications from the remote websocket server about an update of the model.
 */

public class ModelService extends Service implements ModelServiceBinder.IMyServiceMethod {
    private static final String TAG = "ModelService";

    /**
     * Instance that contain the model
     */
    private BigModel model;

    /**
     * Binder related to ModelService,
     * it's an instance of {@link ModelServiceBinder}
     */
    private IBinder binder;

    /**
     * Constructor to initialize the {@link BigModel } instance
     */
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
        if (intent.getAction() == null)
            return;

        switch (intent.getAction()) {
            case WebsocketService.CONNECT_TO_APPLICATION:
                InitializeApplication initializeApplication = intent.getParcelableExtra(WebsocketService.CONNECT_TO_APPLICATION);
                Log.d(TAG, "Connect to application with: " + initializeApplication.getInterventions().size() + " interventions");
                Gson gson = new GsonBuilder().create();
                Log.d(TAG, "RetrievedInitializeApplication: " + gson.toJson(initializeApplication));
                model.setMessageInitialize(initializeApplication);
                sendToEveryone(-1, ModelConstants.INITIALIZE_APPLICATION);
                break;
            case WebsocketService.INTERVENTION_CHOSEN:
                model.setCurrentIntervention(InterventionChosen.createByIntervention(intent.getParcelableExtra("INTERVENTION_CHOSEN")));
                break;
            case WebsocketService.DISCONNECT_TO_APPLICATION:
                model = new BigModel();
                break;
            case WebsocketService.INTERVENTION_CREATED:
                Intervention intervention = intent.getParcelableExtra("INTERVENTION_CREATED");
                model.getMessageInitialize().getInterventions().add(intervention);
                sendToEveryone(intervention.getId(), ModelConstants.ADD_INTERVENTION);
                break;
            case WebsocketService.INTERVENTION_CLOSED:
                int id = intent.getIntExtra(WebsocketService.INTERVENTION_CLOSED, -1);
                model.getMessageInitialize().setInterventionClosedById(intent.getIntExtra("INTERVENTION_CLOSED", -1));
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
                model.getCurrentIntervention()
                        .changeSymbol(symbolUpdated);
                sendToEveryone(symbolUpdated.getId(), ModelConstants.UPDATE_INTERVENTION_UPDATE_SYMBOL);
                break;
            case WebsocketService.INTERVENTION_SYMBOL_DELETED:
                int idSymbol = intent.getIntExtra(WebsocketService.INTERVENTION_SYMBOL_DELETED, -1);
                model.getCurrentIntervention().deleteSymbolById(idSymbol);
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
                model.getCurrentIntervention().changeUnit(unitUpdated);
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
            //Si l'action est mal définit
            default:
                Log.e(TAG, "Erreur d'action non reconnu pour la mise à jour du model");
        }
    }

    /**
     * getter for the Model
     * @return the instance of the {@link BigModel}
     */
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
    public Intervention getIntervention(int id) {
        return model.getMessageInitialize().getInterventionById(id);
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
}
