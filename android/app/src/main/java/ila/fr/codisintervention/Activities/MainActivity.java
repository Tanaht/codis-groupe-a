package ila.fr.codisintervention.Activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import ila.fr.codisintervention.R;
import ila.fr.codisintervention.RabbitMQConstante;
import ila.fr.codisintervention.Services.RabbitReceiver;
import ila.fr.codisintervention.Services.RabbitSender;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        RabbitReceiver rabbitReceiver = new RabbitReceiver();
        rabbitReceiver.addBasicListenerRabbitMQ(RabbitMQConstante.NAME_QUEUE_TEST);

        rabbitReceiver.addBasicListenerRabbitMQ(RabbitMQConstante.NAME_TOPIC1_TEST);
        rabbitReceiver.addBasicListenerRabbitMQ(RabbitMQConstante.NAME_TOPIC2_TEST);

        RabbitSender sender = new RabbitSender();
        sender.sendMessage(RabbitMQConstante.NAME_QUEUE_TEST,"pouet");





        setContentView(R.layout.activity_main);
    }

}
