package ila.fr.codisintervention.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ila.fr.codisintervention.R;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompHeader;
import ua.naiksoftware.stomp.client.StompClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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


        setContentView(R.layout.activity_main);
    }

}
