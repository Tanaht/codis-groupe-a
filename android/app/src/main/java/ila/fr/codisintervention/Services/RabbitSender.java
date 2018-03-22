package ila.fr.codisintervention.Services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by christophe on 22/03/18.
 */

public class RabbitSender {

    private String host;
    private int port;
    private String userName;
    private String password;
    ConnectionFactory factory;
    private String queue;

    public RabbitSender(String host, int port, String queue, String userName, String password){
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.queue = queue;
        this.factory = new ConnectionFactory();

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(userName);
        factory.setPassword(password);
    }

    public void sendMessage(String message){
        Connection connection = null;
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes("UTF-8"));
            System.out.println("message envoyé !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
