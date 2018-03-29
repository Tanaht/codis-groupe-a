package ila.fr.codisintervention.Activities;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.ModelServiceBinder;
import ila.fr.codisintervention.binders.WebsocketServiceBinder;
import ila.fr.codisintervention.models.messages.User;
import ila.fr.codisintervention.services.constants.ModelConstants;
import ila.fr.codisintervention.services.model.ModelService;
import ila.fr.codisintervention.services.websocket.WebsocketService;
import ua.naiksoftware.stomp.client.StompClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private StompClient client;
    private String login;
    private String motDePasse;

    private EditText editText_login;
    private EditText editText_mdp;
    private Button boutonValider;


    // ServiceConnection permet de gérer l'état du lien entre l'activité et le websocketService.
    private ServiceConnection webSocketServiceConnection;
    private ServiceConnection modelServiceConnection;

    private WebsocketServiceBinder.IMyServiceMethod websocketService;
    private ModelServiceBinder.IMyServiceMethod modelService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        editText_login = (EditText) this.findViewById(R.id.editText_login);
        editText_mdp = (EditText) this.findViewById(R.id.editText_mdp);


        boutonValider = ((Button)this.findViewById(R.id.button_valider));
        boutonValider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (verificationSaisie()){
                    connexion();
                }
            }
        });

        bindToService();
    }

    private void bindToService() {
        webSocketServiceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {}
            public void onServiceConnected(ComponentName arg0, IBinder binder) {
                //on récupère l'instance du websocketService dans l'activité
                websocketService = ((WebsocketServiceBinder)binder).getService();

                //on genère l'évènement indiquant qu'on est "bindé"
//                handler.sendEmptyMessage(ON_BIND);
            }
        };

        modelServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                //on récupère l'instance du modelService dans l'activité
                modelService = ((ModelServiceBinder)binder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {}
        };


        // Start the service by the method startService() prevent the service to be free if the activity is free.

        // Binding Activity with WebSocketService
        startService(new Intent(this, WebsocketService.class));
        Intent intent = new Intent(this, WebsocketService.class);
        //lance le binding du websocketService
        bindService(intent, webSocketServiceConnection, Context.BIND_AUTO_CREATE);

        //Binding Activity with ModelService
        startService(new Intent(this, ModelService.class));
        intent = new Intent(this, ModelService.class);
        //lance le binding du websocketService
        bindService(intent, modelServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //on supprimer le binding entre l'activité et le websocketService.
        if(webSocketServiceConnection != null)
            unbindService(webSocketServiceConnection);

        if(modelServiceConnection != null)
            unbindService(modelServiceConnection);
    }

    /**
     * Méthode qui vérifie que l'utilisateur a saisi un login et un mot de passe
     *
     * @return
     */
    private boolean verificationSaisie(){
        login = editText_login.getText().toString().trim();
        motDePasse = editText_mdp.getText().toString().trim();
        if (login.equals("")) {
            Toast.makeText(this, "Vous devez saisir un login", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (motDePasse.equals("")) {
            Toast.makeText(this, "Vous devez saisir un mot de passe", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Méthode qui réalise la connexion avec le serveur.
     * En fonction de la réponse du serveur, l'utilisateur est dirigé vers la page codis ou pompier.
     */
    private void connexion() {
        websocketService.connect(login, motDePasse);
    }


    @Override
    public void onResume() {
        super.onResume();
        // This registers mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(ModelConstants.ACTION_INITIALIZE_APPLICATION));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(WebsocketService.ACTION_AUTHENTICATION_ERROR));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(WebsocketService.ACTION_AUTHENTICATION_SUCCESS));


    }


    // Handling the received Intents for the "my-integer" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "Intent Received: " + intent.getAction());
            // Extract data included in the Intent

            if(ModelConstants.ACTION_INITIALIZE_APPLICATION.equals(intent.getAction())) {
                User user = null; //TODO: retrieve user in model;

                if(user.isCodisUser()) {

                    Intent gotoMainMenuCodis = new Intent( MainActivity.this, MainMenuCodis.class);
                    startActivity(gotoMainMenuCodis);
                }
                if(user.isSimpleUser()) {

                    Intent gotoMainMenuIntervenant = new Intent( MainActivity.this, MainMenuIntervenant.class);
                    startActivity(gotoMainMenuIntervenant);
                }
            }
        }
    };

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

}