package ila.fr.codisintervention;

import android.content.SyncStatusObserver;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    private final static String QUEUE_NAME = "hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("lapommevolante.istic.univ-rennes1.fr");
        factory.setPort(8081);
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = null;
        try {

            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("azerty");
            channel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                    connection.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setContentView(R.layout.activity_main);
    }
}
