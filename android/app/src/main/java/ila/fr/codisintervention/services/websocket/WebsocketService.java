package ila.fr.codisintervention.services.websocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import ila.fr.codisintervention.binders.WebsocketServiceBinder;
import ila.fr.codisintervention.utils.Config;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;

/**
 * Service used to manage websocket api
 * Created by tanaky on 26/03/18.
 */

public class WebsocketService extends Service implements WebsocketServiceBinder.IMyServiceMethod {
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

                        // The string "my-integer" will be used to filer the intent
                        Intent initializeAppIntent = new Intent(ACTION_AUTHENTICATION_SUCCESS_AND_INITIALIZE_APPLICATION);
                        // Adding some data
                        initializeAppIntent.putExtra("message", message.getPayload());
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
}
