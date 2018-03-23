package ila.fr.codisintervention.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ila.fr.codisintervention.R;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private StompClient client;
    private String login;
    private String motDePasse;

    private EditText editText_login;
    private EditText editText_mdp;
    private Button boutonValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "http://192.168.43.226:8080/stomp");

/*
        StompClient client = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "http://192.168.43.226:8080/stomp");


        List<StompHeader> stompHeader = Arrays.asList(
                new StompHeader("userlogin","user"),
                new StompHeader("userpassword","pass"));

        client.connect(stompHeader);


        client.topic("/topic/broadcastTest").subscribe(message -> {
            Log.i(TAG, "Received message: " + message.getPayload());
        });



        client.send("/broadcastTest", "hello").subscribe(
                () -> Log.d(TAG, "Sent data!"),
                error -> Log.e(TAG, "Encountered error while sending data!", error)
        );



        client.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    break;
                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    break;
                case ERROR:
                    Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                    break;
            }
        });
*/

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

        if (login.equals("codis")){
            Intent intent = new Intent( MainActivity.this, MainMenuCodis.class);
            startActivity(intent);
        }
        else if (login.equals("pompier")){
            Intent intent = new Intent( MainActivity.this, MainMenuIntervenant.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Pas de Connexion", Toast.LENGTH_SHORT).show();
        }

    }

}
