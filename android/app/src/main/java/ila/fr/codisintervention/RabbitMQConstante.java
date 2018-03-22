package ila.fr.codisintervention;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConstante {
    public static final String NAME_QUEUE_TEST = "fanout.test";

    public static final String NAME_TOPIC1_TEST = "topic1.test";
    public static final String NAME_TOPIC2_TEST = "topic2.test";

    private static final String HOST_RABBITMQ = "lapommevolante.istic.univ-rennes1.fr";
    private static final int PORT_RABBITMQ = 8081;

    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "admin";

    public static Connection getConnectionRabbitMQ() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(HOST_RABBITMQ);
        factory.setPort(PORT_RABBITMQ);
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        return factory.newConnection();
    }
}
