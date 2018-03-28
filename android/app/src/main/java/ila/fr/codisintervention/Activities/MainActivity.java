package ila.fr.codisintervention.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.binders.WebsocketServiceBinder;
import ila.fr.codisintervention.handlers.WebsocketServiceHandler;
import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;
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

    //code de l'élevenement indiquant que l'activité est bindé avec le service
    private static final int ON_BIND = 1;


    // ServiceConnection permet de gérer l'état du lien entre l'activité et le service.
    private ServiceConnection serviceConnection;
    private WebsocketServiceBinder.IMyServiceMethod service;
    private static Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitializeApplication initializeApplication = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson("{\"user\":{\"username\":\"codis_user\",\"role\":\"ROLE_CODIS_USER\"},\"types\":[{\"label\":\"VSAV\"},{\"label\":\"FPT\"},{\"label\":\"VLCG\"}],\"codes\":[{\"label\":\"INC\",\"description\":\"\"},{\"label\":\"SAP\",\"description\":\"\"}],\"vehicles\":[{\"label\":\"vehicule 1\",\"type\":\"VSAV\",\"status\":\"AVAILABLE\"},{\"label\":\"vehicule 2\",\"type\":\"VSAV\",\"status\":\"AVAILABLE\"},{\"label\":\"vehicule 3\",\"type\":\"FPT\",\"status\":\"AVAILABLE\"},{\"label\":\"vehicule 4\",\"type\":\"FPT\",\"status\":\"AVAILABLE\"},{\"label\":\"vehicule 5\",\"type\":\"VLCG\",\"status\":\"AVAILABLE\"},{\"label\":\"vehicule 6\",\"type\":\"VLCG\",\"status\":\"AVAILABLE\"}],\"demandes\":[],\"interventions\":[{\"id\":1,\"date\":1522159274,\"code\":\"INC\",\"adresse\":\"11 Rue du Bois Perrin\",\"drone_available\":true,\"location\":{\"lat\":48.116486,\"lng\":-1.647416}},{\"id\":2,\"date\":1522159274,\"code\":\"SAP\",\"adresse\":\"Cours des Alliés, 35024 Rennes\",\"drone_available\":true,\"location\":{\"lat\":48.10573,\"lng\":-1.67472}}]}", InitializeApplication.class);


        Log.d(TAG, "json to Object to Json = " + new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(initializeApplication));

        handler = new WebsocketServiceHandler();

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
        serviceConnection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {}
            public void onServiceConnected(ComponentName arg0, IBinder binder) {

                //on récupère l'instance du service dans l'activité
                service = ((WebsocketServiceBinder)binder).getService();

                //on genère l'évènement indiquant qu'on est "bindé"
                handler.sendEmptyMessage(ON_BIND);
            }
        };


        //démarre le service si il n'est pas démarré
        //Le binding du service est configuré avec "BIND_AUTO_CREATE" ce qui normalement
        //démarre le service si il n'est pas démarrer, la différence ici est que le fait de
        //démarrer le service par "startService" fait que si l'activité est détruite, le service
        //reste en vie (obligatoire pour l'application AlarmIngressStyle)
        startService(new Intent(this, WebsocketService.class));
        Intent intent = new Intent(this, WebsocketService.class);
        //lance le binding du service
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //on supprimer le binding entre l'activité et le service.
        if(serviceConnection != null)
            unbindService(serviceConnection);
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
        service.connect(login, motDePasse);
    }


    @Override
    public void onResume() {
        super.onResume();
        // This registers mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(WebsocketService.ACTION_AUTHENTICATION_SUCCESS_AND_INITIALIZE_APPLICATION));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(WebsocketService.ACTION_AUTHENTICATION_ERROR));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(WebsocketService.ACTION_AUTHENTICATION_SUCCESS));


    }


    // Handling the received Intents for the "my-integer" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "Intent Received: " + intent.getAction());
            // Extract data included in the Intent

            if(WebsocketService.ACTION_AUTHENTICATION_SUCCESS_AND_INITIALIZE_APPLICATION.equals(intent.getAction())) {
                Toast.makeText(MainActivity.this, getString(R.string.msg_success_credentials), Toast.LENGTH_LONG).show();

                String json = intent.getStringExtra("message");
                Gson gson = new GsonBuilder().create();
                //TODO: Here we receive the object pushed from server
                InitializeApplication initializeApplication = intent.getParcelableExtra(InitializeApplication.class.getName());


//                TODO: Initialize application for both types of user

                if(initializeApplication.getUser().isCodisUser()) {
//                    Log.i(TAG, initializeApplication.getUser().getUsername() + " is a CODIS USER");
////                        TODO: Initialize application for codis user.
                    Intervention intervention = new Intervention();

                    intervention.setId(10);
                    intervention.setDate(10);
                    intervention.setCode("INC");
                    intervention.setAddress("ISTIC, Bat 12 D, Allée Henri Poincaré, Rennes");
                    intervention.setLocation(new Location(48.1156746,-1.640608));
                    service.createIntervention(intervention);
                    service.chooseIntervention(1);

                } else if(initializeApplication.getUser().isSimpleUser()) {
                    Log.i(TAG, initializeApplication.getUser().getUsername() + " is a SIMPLE USER");

                }
            }

            if(WebsocketService.ACTION_AUTHENTICATION_ERROR.equals(intent.getAction())) {
                Toast.makeText(MainActivity.this, getString(R.string.error_invalid_credentials), Toast.LENGTH_LONG).show();
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