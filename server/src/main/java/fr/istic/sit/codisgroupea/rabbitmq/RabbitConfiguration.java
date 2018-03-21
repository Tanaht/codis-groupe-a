package fr.istic.sit.codisgroupea.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ client.
 */
@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.hostname}")
    private String rabbitmqHostName;
    @Value("${rabbitmq.port}")
    private int rabbitmqPort;
    @Value("${rabbitmq.user}")
    private String rabbitmqUser;

    /* Very temporary */
    @Value("${rabbitmq.password}")
    private String rabbitmqPassword;

    /**
     * Create a new connection factory.
     *
     * @return the newly created connection factory.
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory(rabbitmqHostName, rabbitmqPort);
        cf.setUsername(rabbitmqUser);
        cf.setPassword(rabbitmqPassword);

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
