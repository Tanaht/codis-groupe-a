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
import rx.Subscriber;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;

/**
 * Service used to manage websocket api
 * Created by tanaky on 26/03/18.
 */

public class WebsocketService extends Service implements WebsocketServiceBinder.IMyServiceMethod {
    private static final String TAG = "WebSocketService";

    private static final String USERNAME_HEADER_KEY = "userlogin";
    private static final String PASSWORD_HEADER_KEY = "userpassword";

    private StompClient client;

    private IBinder binder;

    public WebsocketService() {

        String url = "http://{host}:{port}/{uri}"
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
    public boolean connect(String username, String password) {
        Log.d(TAG, "Connect to Server with following credentials: " + username + ", " + password);

        List<StompHeader> stompHeader = Arrays.asList(
                new StompHeader(USERNAME_HEADER_KEY, username),
                new StompHeader(PASSWORD_HEADER_KEY, password));


        this.client.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    Log.d(TAG, "Stomp connection opened");


                    client.topic("/topic/users/" + username + "/initialize-application").subscribe(message -> {
                        Log.i(TAG, "[/initialize-application] Received message: " + message.getPayload());

                        // The string "my-integer" will be used to filer the intent
                        Intent intent = new Intent("initialize-application");
                        // Adding some data
                        intent.putExtra("message", message.getPayload());
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    });

                    client.send("/users/" + username + "/subscribed", "PING").subscribe(
                            () -> Log.d(TAG, "[/subscribed] Sent data!"),
                            error -> Log.e(TAG, "[/subscribed] Error Encountered", error)
                    );
                    break;

                case ERROR:
                    Log.d(TAG, "Thread Name: " + Thread.currentThread().getName());
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    break;

                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    break;
            }
        });

        try {
            client.connect(stompHeader);
            return true;
        } catch(Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean connect(String username, String password, Subscriber<LifecycleEvent> subscriber) {
        Log.d(TAG, "Connect to Server");
        this.client.lifecycle().subscribe(subscriber);

        return connect(username, password);
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
