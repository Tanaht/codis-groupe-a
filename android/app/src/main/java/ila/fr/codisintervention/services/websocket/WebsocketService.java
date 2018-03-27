package ila.fr.codisintervention.services.websocket;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

public class WebsocketService extends Service {
    private static final String TAG = "WebSocketService";

    private static final String USERNAME_HEADER_KEY = "userlogin";
    private static final String PASSWORD_HEADER_KEY = "userpassword";

    private StompClient client;

    public WebsocketService() {
        Log.d(TAG, "Instantiating WebSocket Service");


        this.client = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "http://{host}:{port}/{uri}"
                .replace("{host}", Config.get().getHost())
                .replace("{port}",Integer.toString(Config.get().getPort()))
                .replace("{uri}",Config.get().getUri()));
    }

    /**
     * Connect to websocket using credentials in parameters
     * @param username
     * @param password
     * @param subscriber Subscriber to get notified when the websocket is closed or in error
     */
    public void connect(String username, String password, Subscriber<LifecycleEvent> subscriber) {
        this.client.lifecycle().subscribe(subscriber);

        List<StompHeader> stompHeader = Arrays.asList(
                new StompHeader(USERNAME_HEADER_KEY, username),
                new StompHeader(PASSWORD_HEADER_KEY, password));

        client.connect(stompHeader);
    }

    public boolean isConnected() {
        return this.client.isConnected();
    }

    public void disconnect() {
        this.client.disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
