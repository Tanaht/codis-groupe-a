package ila.fr.codisintervention.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.services.model.ModelService;

/**
 * Created by tanna on 15/04/2018.
 *
 * This interface is used to serve to android components in order for them to be aware of the ModelService
 */
public interface ModelServiceAware {
    String TAG = "ModelServiceAware";

    /**
     * This method has to be called from the Android Component using this interface in order to be aware of ModelService
     * this method will call one of the two followings self.onModelServiceConnected or self.onModelServiceDisconnected
     */
    default ServiceConnection bindModelService() {
        ServiceConnection modelServiceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                onModelServiceDisconnected();
            }
            public void onServiceConnected(ComponentName arg0, IBinder binder) {
                setModelService(((ModelServiceBinder)binder).getService());
                onModelServiceConnected();
            }
        };

        // Binding Activity with WebSocketService
        startService(new Intent(getApplicationContext(), ModelService.class));
        Intent intentmodelService = new Intent(getApplicationContext(), ModelService.class);
        //launch binding of webSocketService
        bindService(intentmodelService, modelServiceConnection, Context.BIND_AUTO_CREATE);

        return modelServiceConnection;
    }

    /**
     * Used to unbind the current android components to ModelService, it's a good practice to allways call this method from the onDestroy() of an Android Component.
     * @param modelServiceConnection
     */
    default void unbindModelService(ServiceConnection modelServiceConnection) {
        if(modelServiceConnection != null)
            unbindService(modelServiceConnection);
    }

    /**
     * Method triggered when the ModelService has been disconnected
     */
    default void onModelServiceDisconnected() {
        Log.w(TAG, "The ModelService is disconnected");
    }

    /**
     * Method triggered when the ModelService is connected
     */
    void onModelServiceConnected();

    /**
     * Method triggered when the ModelService is connected, it return the instance of the service, it's all the point of this interface.
     * @param modelService the ModelService instance
     */
    void setModelService(ModelServiceBinder.IMyServiceMethod modelService);


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
