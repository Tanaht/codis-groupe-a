package fr.istic.sit.codisgroupea.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ client.
 */
@Configuration
public class RabbitConfiguration {
    private static final String SERVER_HOSTNAME = "lapommevolante.istic.univ-rennes1.fr";
    private static final int SERVER_RABBITMQ_PORT = 8081;
    private static final String SERVER_RABBITMQ_USER = "admin";

    /* Very temporary */
    private static final String SERVER_RABBITMQ_PSSWD = "admin";

    /**
     * Create a new connection factory.
     *
     * @return the newly created connection factory.
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory(SERVER_HOSTNAME, SERVER_RABBITMQ_PORT);
        cf.setUsername(SERVER_RABBITMQ_USER);
        cf.setPassword(SERVER_RABBITMQ_PSSWD);

        return cf;
    }

    /**
     * Create a new AMQP admin.
     *
     * @return the newly created AMQP admin.
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /**
     * Create a new rabbit template.
     *
     * @return the newly created rabbit tamplate.
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
}
