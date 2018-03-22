package ila.fr.codisintervention.Services;


import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import ila.fr.codisintervention.RabbitMQConstante;

public class RabbitReceiver {

    private static final String TAG = "RabbitReceiver";
    private Connection connRabbitMQ;

    public RabbitReceiver() {
        Connection rabbitMQConnection = null;
        try {
            rabbitMQConnection = RabbitMQConstante.getConnectionRabbitMQ();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


        this.connRabbitMQ = rabbitMQConnection;
    }


    public void addBasicListenerRabbitMQ(String nameQueue){

        try {
            Channel channel = connRabbitMQ.createChannel();

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    Log.i(TAG, "Received: " + message);
                }
            };

            channel.basicConsume(nameQueue, true, consumer);

            Log.i(TAG, "add listener to channel : "+nameQueue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
