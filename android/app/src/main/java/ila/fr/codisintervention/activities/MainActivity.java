package ila.fr.codisintervention.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.binders.WebsocketServiceBinder;
import ila.fr.codisintervention.models.messages.User;
import ila.fr.codisintervention.services.constants.ModelConstants;
import ila.fr.codisintervention.services.model.ModelService;
import ila.fr.codisintervention.services.websocket.WebsocketService;

/**
 * Entrypoint Activity of Application
 * In this activity user has to fill his credentials to attempt connection to the application.
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * EditText used to show to user a username editable field and retrieve value from it
     */
    private EditText usernameEditText;

    /**
     * EditText used to show to user a password editable field and retrieve value from it
     */
    private EditText passwordEditText;

    /**
     * ServiceConnection instance with the WebSocketService
     */
    private ServiceConnection webSocketServiceConnection;

    /**
     * Interface delivered by WebSocketService to be used by other android Component.
     */
    private WebsocketServiceBinder.IMyServiceMethod webSocketService;

    /**
     * ServiceConnection instance with the ModelService
     */
    private ServiceConnection modelServiceConnection;

    /**
     * Interface delivered by ModelService to be used by other android Component, the purpose of this is to update the model.
     */
    private ModelServiceBinder.IMyServiceMethod modelService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = (EditText) this.findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) this.findViewById(R.id.passwordEditText);

        //TODO: Ugly, but less with lambda, see the wiki about coding convention: Used android:onClick in related xml layout file to avoid subscribing listener in the code.
        this.findViewById(R.id.submitButton).setOnClickListener(view -> attemptConnection());

        bindToService();
    }

    /**
     * TODO: It could be mutualized because almost all Activities has to bind to ModelService or WebSocketService -> A separated class that do that has to be created ! like an Interface ModelServiceAware and WebsocketServiceAware, or a superclass Activity aware of services
     * Method used to bind InterventionListActivity to WebsocketService and ModelService, with that, MainActivity is aware of ModelService and WebSocketService
     */
    private void bindToService() {
        webSocketServiceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The Service " + name + " is disconnected");
            }
            public void onServiceConnected(ComponentName arg0, IBinder binder) {
                webSocketService = ((WebsocketServiceBinder)binder).getService();
            }
        };

        modelServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                modelService = ((ModelServiceBinder)binder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.w(TAG, "The Service " + name + " is disconnected");
            }
        };

        //Binding Activity with ModelService
        startService(new Intent(getApplicationContext(), ModelService.class));
        Intent intentModelService = new Intent(getApplicationContext(), ModelService.class);
        //launch binding of ModelService
        bindService(intentModelService, modelServiceConnection, Context.BIND_AUTO_CREATE);


        // Binding Activity with WebSocketService
        startService(new Intent(getApplicationContext(), WebsocketService.class));
        Intent intentWebsocketService = new Intent(getApplicationContext(), WebsocketService.class);
        //launch binding of webSocketService
        bindService(intentWebsocketService, webSocketServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Check credential entered by user to test if it met with the validation criteria and if it is, attempt a connection with the server through {@link WebsocketService}.
     * FIXME: Due to WebSocket Stomp implementation bug, we cannot know if the connection was successfull or not.
     */
    private void attemptConnection(){
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(username.length() == 0 || password.length() == 0) {
            Toasty.error(getApplicationContext(), getString(R.string.error_incomplete_credentials), Toast.LENGTH_SHORT, true).show();
        } else {
            webSocketService.connect(username, password);
        }
    }

    /**
     * Subscribe to Intents that will be used to update this Activity Model.
     */
    @Override
    public void onResume() {
        super.onResume();
        // This registers mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(ModelConstants.ACTION_INITIALIZE_APPLICATION));
    }

    /**
     * TODO: To mutualize equally with BindToService method
     * Define BroadcoastReceiver Instance to get aware when an Intent is send to this activity among other
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "Intent Received: " + intent.getAction());
            // Extract data included in the Intent

            if(ModelConstants.ACTION_INITIALIZE_APPLICATION.equals(intent.getAction())) {
                User user = modelService.getUser();

                if(user.isCodisUser()) {

                    Intent gotoMainMenuCodis = new Intent( MainActivity.this, codisMainMenu.class);
                    startActivity(gotoMainMenuCodis);
                }
                if(user.isSimpleUser()) {

                    Intent gotoMainMenuIntervenant = new Intent( MainActivity.this, MainMenuIntervenant.class);
                    startActivity(gotoMainMenuIntervenant);
                }
            }
        }
    };


    /**
     * Unsubscribe from Broadcoast Receiver instance
     */
    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    /**
     * We Unbind from binded service here to avoid memory leaks
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webSocketServiceConnection != null)
            unbindService(webSocketServiceConnection);

        if(modelServiceConnection != null)
            unbindService(modelServiceConnection);
    }

}