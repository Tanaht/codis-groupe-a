package ila.fr.codisintervention.services.websocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ila.fr.codisintervention.binders.WebSocketServiceBinder;
import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.messages.Request;
import ila.fr.codisintervention.models.messages.DronePing;
import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.PathDrone;
import ila.fr.codisintervention.models.messages.Payload;
import ila.fr.codisintervention.models.messages.Photo;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.SymbolsMessage;
import ila.fr.codisintervention.models.messages.User;
import ila.fr.codisintervention.models.model.InterventionModel;
import ila.fr.codisintervention.services.model.ModelService;
import ila.fr.codisintervention.utils.Config;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

import static ila.fr.codisintervention.services.constants.ModelConstants.UPDATE_DRONE_POSITION;

/**
 * Service used to manage websocket api
 * Created by tanaky on 26/03/18.
 */
@SuppressWarnings("squid:S1192")
public class WebsocketService extends Service implements WebSocketServiceBinder.IMyServiceMethod {
    private static final String TAG = "WebSocketService";

    //FIXME: Export all the constants related to Intent Action in a separacted empty class. For code purpose an Intent action must be a string constant and not an enum.
    /**
     * The constant CONNECT_TO_APPLICATION.
     * Define the Intent action send to model when the user account is connected and the server has send initialize application data.
     */
    public static final String CONNECT_TO_APPLICATION = "CONNECT_TO_APPLICATION";//initialize-application
    /**
     * The constant DISCONNECT_TO_APPLICATION.
     * Define the Intent action send to model when the user account is disconnected.
     */
    public static final String DISCONNECT_TO_APPLICATION = "DISCONNECT_TO_APPLICATION";
    /**
     * The constant INTERVENTION_CREATED.
     * Define the Intent action send in case of intervention created event send from Server
     */
    public static final String INTERVENTION_CREATED = "INTERVENTION_CREATED";
    /**
     * The constant INTERVENTION_CLOSED.
     * Define the Intent action send in case of intervention closed event send from Server
     */
    public static final String INTERVENTION_CLOSED = "INTERVENTION_CLOSED";
    /**
     * The constant INTERVENTION_CHOSEN.
     * Define the Intent action send in case of intervention chosen event send from Server
     */
    public static final String INTERVENTION_CHOSEN = "INTERVENTION_CHOSEN";
    /**
     * The constant INTERVENTION_SYMBOL_CREATED.
     * Define the Intent action send in case of created Symbols send from Server
     */
    public static final String INTERVENTION_SYMBOL_CREATED = "INTERVENTION_SYMBOL_CREATED";
    /**
     * The constant INTERVENTION_SYMBOL_UPDATED.
     * Define the Intent action send in case of Updated Symbols send from Server
     */
    public static final String INTERVENTION_SYMBOL_UPDATED = "INTERVENTION_SYMBOL_UPDATED";
    /**
     * The constant INTERVENTION_SYMBOL_DELETED.
     * Define the Intent action send in case of Deleted Symbols send from Server
     */
    public static final String INTERVENTION_SYMBOL_DELETED = "INTERVENTION_SYMBOL_DELETED";
    /**
     * The constant INTERVENTION_UNIT_CREATED.
     * Define the Intent action send in case of created units send from Server
     */
    public static final String INTERVENTION_UNIT_CREATED = "INTERVENTION_UNIT_CREATED";
    /**
     * The constant INTERVENTION_UNIT_UPDATED.
     * Define the Intent action send in case of updated Units send from Server
     */
    public static final String INTERVENTION_UNIT_UPDATED = "INTERVENTION_UNIT_UPDATED";

    /**
     * The constant DEMANDE_ACCEPTED.
     * Define the Intent action send in case of Accepted Demand send from Server
     */
    public static final String DEMANDE_ACCEPTED = "DEMANDE_ACCEPTED";

    /**
     * The constant DEMANDE_DENIED.
     * Define the Intent action send in case of Denied Demand send from Server
     */
    public static final String DEMANDE_DENIED = "DEMANDE_DENIED";

    /**
     * The constant DEMANDE_CREATED.
     * Define the Intent action send in case of Created Demand send from Server
     */
    public static final String DEMANDE_CREATED = "DEMANDE_CREATED";
    /**
     * The constant DRONE_PING.
     * Define the Intent action send in case of Receive Drone Ping from server
     */
    public static final String DRONE_PING = "DRONE_PING";
    /**
     * The constant DRONE_PHOTO.
     * Define the Intent action send in case of Received Photo from server
     */
    public static final String DRONE_PHOTO = "DRONE_PHOTO";

    /**
     * The constant PROTOCOL_ERROR.
     * Define the Intent action send in case of ERROR Websocket
     */
    public static final String PROTOCOL_ERROR = "PROTOCOL_ERROR";
    /**
     * The constant PROTOCOL_CLOSE.
     * Define the Intent action send in case of CLOSE Websocket
     */
    public static final String PROTOCOL_CLOSE = "PROTOCOL_CLOSE";


    /**
     * The constant DRONE_PATH_RECEIVED
     */
    public static final String DRONE_PATH_RECEIVED = "DRONE_PATH_RECEIVED";

    private static final String USERNAME_HEADER_KEY = "userlogin";

    @SuppressWarnings("squid:S2068")
    private static final String PASSWORD_HEADER_KEY = "userpassword";

    private StompClient client;

    private IBinder binder;
    private String url;


    /**
     * websocket service constructor
     * set the remote host url
     * instantiate the client Stomp object
     */
    public WebsocketService() {

        this.url = "http://{host}:{port}/{uri}"
                .replace("{host}", Config.get().getHost())
                .replace("{port}",Integer.toString(Config.get().getPort()))
                .replace("{uri}",Config.get().getUri());


        Log.d(TAG, "Instantiating WebSocket Service and connect to remote at " + url);

        this.client = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new WebSocketServiceBinder(this);
    }


    @Override
    public  void connect(String username, String password) {

        Log.d(TAG, "Connect to Server with following credentials: " + username + ", " + password);

        this.client = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);

        List<StompHeader> stompHeader = Arrays.asList(
                new StompHeader(USERNAME_HEADER_KEY, username),
                new StompHeader(PASSWORD_HEADER_KEY, password));


        this.client.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    Log.d(TAG, "STOMP CONNECTION OPENED");

                    client.topic("/topic/users/" + username + "/initialize-application").subscribe(message -> {
                        Log.i(TAG, "[/initialize-application] Received message: " + message.getPayload());

                        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

                        InitializeApplication initializeApplication = gson.fromJson(message.getPayload(), InitializeApplication.class);

                        Log.d(TAG, "JsonToObject of InitializeApplication, retrieved: " + initializeApplication.getInterventions().size() + " interventions");
                        this.performInitializationSubscription(initializeApplication);


                        Intent initializeAppIntent  = new Intent(getApplicationContext(), ModelService.class);
                        initializeAppIntent.setAction(CONNECT_TO_APPLICATION);
                        initializeAppIntent.putExtra(CONNECT_TO_APPLICATION, initializeApplication);
                        getApplicationContext().startService(initializeAppIntent);

                    });

                    client.send("/users/" + username + "/subscribed", "PING").subscribe(
                            () -> Log.d(TAG, "[/subscribed] Sent data!"),
                            error -> Log.e(TAG, "[/subscribed] Error Encountered", error)
                    );
                    break;

                case ERROR:
                    Log.d(TAG, "STOMP CONNECTION ERROR");

                    // Notify Registered Activity from ERROR Connection
                    //Toasty.error(getApplicationContext(), getString(R.string.error_connection_error), Toast.LENGTH_SHORT);
                    Intent errorIntent = new Intent(PROTOCOL_ERROR);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(errorIntent);
                    break;

                case CLOSED:
                    Log.d(TAG, "STOMP CONNECTION CLOSED");
                    // Notify Registered Activity from CLOSE Connection

                    //Toasty.error(getApplicationContext(), getString(R.string.error_connection_close), Toast.LENGTH_SHORT);
                    Intent closeIntent  = new Intent(PROTOCOL_CLOSE);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(closeIntent);
                    break;
            }
        });

        try {
            client.connect(stompHeader);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     *
     * Connect a client to the websocket and return true if connected
     * We use connect method and we manage bad credentials
     * login : username and password
     */
    public boolean connectService(String username, String password){
        connect(username, password);
        // manage a bad credential
            int i=0;
            while(!isConnected() && i<10){
                try {
                    Thread.sleep(100);
                } catch(Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                i++;
            }
            if (!isConnected()) {
                disconnect();
            }
        return isConnected();
    }

    @Override
    public boolean isConnected() {
        return this.client.isConnected();
    }

    @Override
    public void disconnect() {
        this.client.disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * Send Creation intervention notification to server
     * {
         code: String,
         address: String,
         location: {
             lat: float,
             lng: float
         }
       }
     */
    @Override
    public void createIntervention(InterventionModel interventionModel) {

        Intervention intervention = new Intervention(interventionModel);

        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                if(f.getDeclaringClass().equals(Intervention.class)) {
                    switch (f.getName()) {
                        case "drone_available":
                            return true;
                        case "id":
                            return true;
                        case "date":
                            return true;
                        default:
                    }
                }
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();


        Log.i(TAG, "[/app/interventions/create] with message " + gson.toJson(intervention));
        this.client.send("/app/interventions/create", gson.toJson(intervention)).subscribe(
                () -> Log.d(TAG, "[/app/interventions/create] Sent data!"),
                error -> Log.e(TAG, "[/app/interventions/create] Error Encountered", error)
        );
    }

    /**
    * Subscribe to this and send notification to server
    * /topic/interventions/{id}/symbols/event`
    * `/topic/interventions/{id}/units/event`
    * `/topic/interventions/{id}/units/{idUnit}/denied`
    * `/topic/interventions/{id}/units/{idUnit}/accepted`
    * `/topic/interventions/{id}/drone/ping`
    * */
    @Override
    public void chooseIntervention(int id){
        this.client.topic("/topic/interventions/" + id + "/symbols/event").subscribe(message -> {
            Log.i(TAG, "[/topic/interventions/" + id + "/units/event] Received message: " + message.getPayload());
            deliverSymbolsEventIntents(message);
        });


        this.client.topic("/topic/interventions/" + id + "/units/event").subscribe(message -> {
            Log.i(TAG, "[/topic/interventions/" + id + "/units/event] Received message: " + message.getPayload());
        });

        this.client.send("/app/interventions/" + id + "/choose", "PING").subscribe(
                () -> Log.d(TAG, "[/app/interventions/" + id + "/choose] Sent data!"),
                error -> Log.e(TAG, "[/app/interventions/" + id + "/choose] Error Encountered", error)
        );

        this.client.topic("/topic/interventions/" + id + "/drone/ping").subscribe(message -> {
            Log.i(TAG, "[/topic/interventions/" + id + "/drone/ping] Received message: " + message.getPayload());
            deliverDroneLocation(message);
        });

        this.client.topic("/topic/interventions/" + id + "/drone/path").subscribe(message -> {
            Log.i(TAG, "[/topic/interventions/" + id + "/drone/path] Received message: " + message.getPayload());

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            
            Intent dronePathReceived = new Intent(getApplicationContext(), ModelService.class);
            dronePathReceived.setAction(DRONE_PATH_RECEIVED);
            dronePathReceived.putExtra(DRONE_PATH_RECEIVED, gson.fromJson(message.getPayload(), PathDrone.class));
            getApplicationContext().startService(dronePathReceived);
        });

    }


    /**
     * Class triggered when receiving events of type "/topic/interventions/{id}/symbols/event"
     * It send the correct intent.
     * @param message
     */
    private void deliverSymbolsEventIntents(StompMessage message) {
        //FIXME: Refactoring this method can be done with enum (EventKind, EventType) or other class architecture

        String type = "";

        ArrayList<Symbol> symbols = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(message.getPayload());

            if(!object.has("type"))
                throw new JSONException("JSON Message must have a 'type' key");

            type = object.getString("type");

            if(!Arrays.asList("CREATE", "UPDATE", "DELETE").contains(type)) {
                throw new JSONException("JSON Message 'type' key must be one of the following: 'CREATE|UPDATE|DELETE'");
            }

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


            SymbolsMessage symbMsg = gson.fromJson(message.getPayload(), SymbolsMessage.class);
            //FIXME: Ugly but this must works. Need to test other way
            symbols = new ArrayList<>(symbMsg.getSymbols());


        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        Intent symbolActionIntent  = new Intent(getApplicationContext(), ModelService.class);

        if("CREATE".equals(type)) {
            symbolActionIntent.setAction(INTERVENTION_SYMBOL_CREATED);
            symbolActionIntent.putExtra(INTERVENTION_SYMBOL_CREATED, symbols);
        }

        if("UPDATE".equals(type)) {
            symbolActionIntent.setAction(INTERVENTION_SYMBOL_UPDATED);
            symbolActionIntent.putExtra(INTERVENTION_SYMBOL_UPDATED, symbols);
        }

        if("DELETE".equals(type)) {
            symbolActionIntent.setAction(INTERVENTION_SYMBOL_DELETED);
            symbolActionIntent.putExtra(INTERVENTION_SYMBOL_DELETED, symbols);
        }
        getApplicationContext().startService(symbolActionIntent);
    }

    /**
     * Deliver Received Drone Real-Location
     * url : "/topic/interventions/{id}/drone/ping"
     * @param message
     */
    private void deliverDroneLocation(StompMessage message) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        DronePing dronePing = gson.fromJson(message.getPayload(), DronePing.class);

        Intent toBeBroadcoastedIntent = new Intent(UPDATE_DRONE_POSITION);
        toBeBroadcoastedIntent.putExtra(UPDATE_DRONE_POSITION, dronePing);
        LocalBroadcastManager.getInstance(this).sendBroadcast(toBeBroadcoastedIntent);
    }

    @Override
    public void updateSymbols(int interventionId, List<ila.fr.codisintervention.models.model.map_icon.symbol.Symbol> symbols) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(symbols);
        Log.d(TAG, "[/app/interventions/" + interventionId + "/symbols/create] with message " + json);
        this.client.send("/app/interventions/" + interventionId + "/symbols/update", json).subscribe(
                () -> Log.w(TAG, "[/app/interventions/" + interventionId + "/symbols/update] Sent data!"),
                error -> Log.e(TAG, "[/app/interventions/" + interventionId + "/symbols/update] Error Encountered", error)
        );
    }

    @Override
    public void createSymbols(int interventionId, List<ila.fr.codisintervention.models.model.map_icon.symbol.Symbol> symbols) {
        List<Symbol> listSymbols = new ArrayList<>();
        for (ila.fr.codisintervention.models.model.map_icon.symbol.Symbol symb : symbols){
            listSymbols.add(new Symbol(symb));
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().equals("id");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();

        String json = gson.toJson(listSymbols);
        Log.d(TAG, "[/app/interventions/" + interventionId + "/symbols/create] with message " + json);
        this.client.send("/app/interventions/" + interventionId + "/symbols/create", json).subscribe(
                () -> Log.w(TAG, "[/app/interventions/" + interventionId + "/symbols/create] Sent data!"),
                error -> Log.e(TAG, "[/app/interventions/" + interventionId + "/symbols/create] Error Encountered", error)
        );
    }

    /**
     * In this method we send to the server the symbol list we want to delete
     * @param interventionId
     * @param symbs
     */
    @Override
    public void deleteSymbols(int interventionId, List<ila.fr.codisintervention.models.model.map_icon.symbol.Symbol> symbs) {
        List<Symbol> symbols = new ArrayList<>();
        for (ila.fr.codisintervention.models.model.map_icon.symbol.Symbol symb : symbs){
            symbols.add(new Symbol(symb));
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return Arrays.asList("shape", "color").contains(f.getName());
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return Arrays.asList(Location.class, Payload.class).contains(clazz);
            }
        }).create();

        String gsonData = gson.toJson(symbols);

        this.client.send("/app/interventions/" + interventionId + "/symbols/delete", gsonData).subscribe(
                () -> Log.d(TAG, "[/app/interventions/" + interventionId + "/symbols/delete] Sent data!"),
                error -> Log.e(TAG, "[/app/interventions/" + interventionId + "/symbols/delete] Error Encountered", error)
        );
    }

    @Override
    public void createPathDrone(int interventionId, PathDrone path) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(path);

        this.client.send("/app/interventions/" + interventionId + "/drone/path", json).subscribe(
                () -> Log.d(TAG, "[/app/interventions/" + interventionId + "/drone/path] Sent data: "+json),
                error -> Log.e(TAG, "[/app/interventions/" + interventionId + "/drone/path] Error Encountered", error)
        );

    }

    /**
     * In this method we initialize all the required subscription to websockets channels according to logged in user.
     *
     * @param initializeApplication the initialize application
     */
    public void performInitializationSubscription(InitializeApplication initializeApplication) {
        User user = initializeApplication.getUser();

        this.client.topic("/topic/interventions/created").subscribe(message -> {
            Log.i(TAG, "[/interventions/created] Received message: " + message.getPayload());

            Intent interventionCreated  = new Intent(getApplicationContext(), ModelService.class);

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return false;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return clazz.equals(Photo.class);
                }
            }).create();

            interventionCreated.setAction(INTERVENTION_CREATED);
            interventionCreated.putExtra(INTERVENTION_CREATED, gson.fromJson(message.getPayload(), Intervention.class));
            getApplicationContext().startService(interventionCreated);

        });


        this.client.topic("/topic/interventions/closed").subscribe(message -> {
            Log.i(TAG, "[/interventions/closed] Received message: " + message.getPayload());


            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return Arrays.asList("date", "code", "address", "drone_available").contains(f.getName());
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return Arrays.asList(Photo.class, Location.class).contains(clazz);
                }
            }).create();


            Intent interventionClosed  = new Intent(getApplicationContext(), ModelService.class);
            interventionClosed.setAction(INTERVENTION_CLOSED);
            getApplicationContext().startService(interventionClosed);
        });


        this.client.topic("/topic/users/" + user.getUsername() + "/intervention-chosen").subscribe(message -> {
            Log.i(TAG, "[/users/" + user.getUsername() + "/intervention-chosen] Received message: " + message.getPayload());

            Intent interventionChosen  = new Intent(getApplicationContext(), ModelService.class);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            interventionChosen.setAction(INTERVENTION_CHOSEN);
            Intervention interv = gson.fromJson(message.getPayload(), Intervention.class);
            interventionChosen.putExtra(INTERVENTION_CHOSEN, interv);
            getApplicationContext().startService(interventionChosen);

        });


        if(user.isCodisUser()) {
            this.client.topic("/topic/demandes/created").subscribe(message -> {
                Log.i(TAG, "[/demandes/created] Received message: " + message.getPayload());
            });

            performDemandeSubscriptionInitialization(initializeApplication.getDemandes());

        }
    }

    /**
     * In this channel we subscribe to channels of available requests
     * @param requests
     */
    private void performDemandeSubscriptionInitialization(List<Request> requests) {
        for(Request request : requests) {

            this.client.topic("/topic/requests/" + request.getId() + "/accepted").subscribe(message -> {
                Log.i(TAG, "[/topic/requests/" + request.getId() + "/accepted] Received message: " + message.getPayload());
            });
            this.client.topic("/topic/requests/" + request.getId() + "/denied").subscribe(message -> {
                Log.i(TAG, "[/topic/requests/" + request.getId() + "/denied] Received message: " + message.getPayload());
            });
        }
    }


}
