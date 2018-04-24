package ila.fr.codisintervention.activities;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.binders.WebSocketServiceBinder;
import ila.fr.codisintervention.services.ModelServiceAware;
import ila.fr.codisintervention.services.WebSocketServiceAware;

/**
 * Stub to be suppressed as soon as possible
 *
 * Exemple activity to demonstrate how to subscribe to service
 * TODO: Suppress when unused
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class SecondMainActivity extends AppCompatActivity implements WebSocketServiceAware, ModelServiceAware{
    private static final String TAG = "SecondMainActivity";

    /**
     * Service connection of the service subscribed
     */
    private ServiceConnection webSocketServiceConnection;

    /**
     * Service connection of the service subscribed
     */
    private ServiceConnection modelServiceConnection;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Here we call the aware interface to perform the binding at our place, we gain an instance of ServiceConnection
        Log.d(TAG, "Bind to services");
        modelServiceConnection = bindModelService();
        webSocketServiceConnection = bindWebSocketService();
    }


    @Override
    public void onModelServiceConnected() {
        Log.i(TAG, "ModelServiceConnected");
    }

    @Override
    public void setModelService(ModelServiceBinder.IMyServiceMethod modelService) {
        Log.d(TAG, "Here we gain an instance of modelService: " + modelService);
    }

    @Override
    public void onWebSocketServiceConnected() {
        Log.d(TAG, "WebSocketServiceConnected");
    }

    @Override
    public void setWebSocketService(WebSocketServiceBinder.IMyServiceMethod webSocketService) {
        Log.d(TAG, "Here we gain an instance of webSocketService: " + webSocketService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //We unbind from the registered services
        unbindModelService(modelServiceConnection);
        unbindWebSocketService(webSocketServiceConnection);
    }

    public void onClick(View view) {
        Log.d(TAG, "On Click");
    }
}
