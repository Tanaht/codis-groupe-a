package ila.fr.codisintervention.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import ila.fr.codisintervention.binders.WebSocketServiceBinder;
import ila.fr.codisintervention.services.websocket.WebsocketService;

/**
 * Created by tanna on 15/04/2018.
 *
 * This interface is used to serve to android components in order for them to be aware of the WebSocketService
 */
public interface WebSocketServiceAware {
    String TAG = "WebSocketServiceAware";

    /**
     * This method has to be called from the Android Component using this interface in order to be aware of WebSocketService
     * this method will call one of the two followings self.onModelServiceConnected or self.onModelServiceDisconnected
     */
    default ServiceConnection bindWebSocketService() {
        ServiceConnection webSocketServiceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                onWebSocketServiceDisconnected();
            }
            public void onServiceConnected(ComponentName arg0, IBinder binder) {
                setWebSocketService(((WebSocketServiceBinder)binder).getService());
                onWebSocketServiceConnected();
            }
        };

        // Binding Activity with WebSocketService
        startService(new Intent(getApplicationContext(), WebsocketService.class));
        Intent intentWebsocketService = new Intent(getApplicationContext(), WebsocketService.class);
        //launch binding of webSocketService
        bindService(intentWebsocketService, webSocketServiceConnection, Context.BIND_AUTO_CREATE);

        return webSocketServiceConnection;
    }

    /**
     * Used to unbind the current android components to WebSocketService, it's a good practice to allways call this method from the onDestroy() of an Android Component.
     * @param webSocketServiceConnection
     */
    default void unbindWebSocketService(ServiceConnection webSocketServiceConnection) {
        if(webSocketServiceConnection != null)
            unbindService(webSocketServiceConnection);
    }

    /**
     * Method triggered when the WebSocketService has been disconnected
     */
    default void onWebSocketServiceDisconnected() {
        Log.w(TAG, "The WebSocketService is disconnected");
    }

    /**
     * Method triggered when the WebSocketService is connected
     */
    void onWebSocketServiceConnected();

    /**
     * Method triggered when the WebSocketService is connected, it return the instance of the service, it's all the point of this interface.
     * @param webSocketService the WebSocketService instance
     */
    void setWebSocketService(WebSocketServiceBinder.IMyServiceMethod webSocketService);


    /**
     * Stub from the one defined in {@link android.support.v7.app.AppCompatActivity}
     * @param service
     * @return
     */
    ComponentName startService(Intent service);

    /**
     * Stub from the one defined in {@link android.support.v7.app.AppCompatActivity}
     * @return
     */
    Context getApplicationContext();

    /**
     * Stub from the one defined in {@link android.support.v7.app.AppCompatActivity}
     * @param service
     * @param conn
     * @param flags
     * @return
     */
    boolean bindService(Intent service, ServiceConnection conn, int flags);


    /**
     * Stub from the one defined in {@link android.support.v7.app.AppCompatActivity}
     * @param conn
     */
    void unbindService(ServiceConnection conn);
}
