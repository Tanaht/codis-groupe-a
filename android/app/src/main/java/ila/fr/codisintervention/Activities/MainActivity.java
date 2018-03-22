package ila.fr.codisintervention.Activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.Services.RabbitSender;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        RabbitSender sender = new RabbitSender("lapommevolante.istic.univ-rennes1.fr", 8081,
                "fanout.test","admin", "admin");
        sender.sendMessage("pouet");

        setContentView(R.layout.activity_main);
    }

}
