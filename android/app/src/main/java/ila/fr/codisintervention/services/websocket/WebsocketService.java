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

import java.util.Arrays;
import java.util.List;

import ila.fr.codisintervention.binders.WebsocketServiceBinder;
import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.User;
import ila.fr.codisintervention.utils.Config;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;

/**
 * Service used to manage websocket api
 * Created by tanaky on 26/03/18.
 */

public class WebsocketService extends Service implements WebsocketServiceBinder.IMyServiceMethod {
    public static enum IntentAction {
        CONNECT_TO_APPLICATION,//initialize-application
        DISCONNECT_TO_APPLICATION,
        INTERVENTION_CREATED,
        INTERVENTION_CLOSED,
        INTERVENTION_SYMBOL_CREATED,
        INTERVENTION_SYMBOL_UPDATED,
        INTERVENTION_SYMBOL_DELETED,
        INTERVENTION_UNIT_CREATED,
        INTERVENTION_UNIT_UPDATED,
        INTERVENTION_SYMBOL_ACCEPTED,
        INTERVENTION_SYMBOL_DENIED,
        DEMANDE_ACCEPTED,
        DEMANDE_DENIED,
        DEMANDE_CREATED


    }


    public static final String ACTION_AUTHENTICATION_SUCCESS_AND_INITIALIZE_APPLICATION = "initialize-application";
    public static final String ACTION_AUTHENTICATION_ERROR = "authentication-error";

    private static final String TAG = "WebSocketService";

    private static final String USERNAME_HEADER_KEY = "userlogin";
    private static final String PASSWORD_HEADER_KEY = "userpassword";

    private StompClient client;

    private IBinder binder;
    private String url;

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
        Log.d(TAG, "OnCreate WebSocket Service");
        binder = new WebsocketServiceBinder(this);
    }


    @Override
    public  void connect(String username, String password) {
        Log.d(TAG, "Connect to Server with following credentials: " + username + ", " + password);

        if(client.isConnected() || client.isConnecting()) {
            client.disconnect();
            this.client = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);
        }

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

                        this.performInitializationSubscription(initializeApplication.getUser());
                        // The string "my-integer" will be used to filer the intent
                        Intent initializeAppIntent = new Intent(ACTION_AUTHENTICATION_SUCCESS_AND_INITIALIZE_APPLICATION);
                        // Adding some data
                        initializeAppIntent.putExtra(InitializeApplication.class.getName(), initializeApplication);

                        LocalBroadcastManager.getInstance(this).sendBroadcast(initializeAppIntent);
                    });

                    client.send("/users/" + username + "/subscribed", "PING").subscribe(
                            () -> Log.d(TAG, "[/subscribed] Sent data!"),
                            error -> Log.e(TAG, "[/subscribed] Error Encountered", error)
                    );
                    break;

                case ERROR:
                    Log.d(TAG, "STOMP CONNECTION ERROR");

                    // Notify Registered Activity from SUCCESS AUTH
                    Intent errorAuthIntent = new Intent(ACTION_AUTHENTICATION_ERROR);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(errorAuthIntent);
                    break;

                case CLOSED:
                    Log.d(TAG, "STOMP CONNECTION CLOSED");
                    break;
            }
        });

        try {
            client.connect(stompHeader);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
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
    public void createIntervention(Intervention intervention) {
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
//                Log.d(TAG, "Name: '" + f.getName() + "' DeclaredClass: '" + f.getDeclaredClass() + "' DeclaringClass: '" + f.getDeclaringClass() + "'");
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
    * */
    @Override
    public void chooseIntervention(int id){
        this.client.topic("/topic/interventions/" + id + "/symbols/event").subscribe(message -> {
            Log.i(TAG, "[/topic/interventions/" + id + "/units/event] Received message: " + message.getPayload());
        });


        this.client.topic("/topic/interventions/" + id + "/units/event").subscribe(message -> {
            Log.i(TAG, "[/topic/interventions/" + id + "/units/event] Received message: " + message.getPayload());
        });

        this.client.send("/app/interventions/" + id + "/choose", "PING").subscribe(
                () -> Log.d(TAG, "[/app/interventions/" + id + "/choose] Sent data!"),
                error -> Log.e(TAG, "[/app/interventions/" + id + "/choose] Error Encountered", error)
        );
//        this.client.topic("/topic/interventions/" + id + "/units/event");
//        this.client.topic("/topic/interventions/" + id + "/units/event");




    }

    public void performInitializationSubscription(User user) {
        this.client.topic("/topic/interventions/created").subscribe(message -> {
            Log.i(TAG, "[/interventions/created] Received message: " + message.getPayload());
        });


        this.client.topic("/topic/interventions/closed").subscribe(message -> {
            Log.i(TAG, "[/interventions/closed] Received message: " + message.getPayload());
        });


        this.client.topic("/topic/users/" + user.getUsername() + "/intervention-chosen").subscribe(message -> {
            Log.i(TAG, "[/users/" + user.getUsername() + "/intervention-chosen] Received message: " + message.getPayload());
        });


        if(user.isSimpleUser()) {

        } else if(user.isCodisUser()) {
            this.client.topic("/topic/demandes/created").subscribe(message -> {
                Log.i(TAG, "[/demandes/created] Received message: " + message.getPayload());
            });
        }
    }
}
