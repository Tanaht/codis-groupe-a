package ila.fr.codisintervention.Services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import ila.fr.codisintervention.RabbitMQConstante;

public class RabbitSender {

    private Connection connRabbitMQ;

    public RabbitSender(){
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

    public void sendMessage(String nameQueue, String message){
        try {
            Channel channel = connRabbitMQ.createChannel();
            channel.queueDeclare(nameQueue, false, false, false, null);
            channel.basicPublish("", nameQueue, null, message.getBytes("UTF-8"));
            System.out.println("message envoyé !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
