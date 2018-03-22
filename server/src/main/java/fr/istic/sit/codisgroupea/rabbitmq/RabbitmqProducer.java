package fr.istic.sit.codisgroupea.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Method run executed when the spring server is launch. used for initialise data in some RabbitMQ channel
 */
@Component
public class RabbitmqProducer implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    /**
     * don't use (spring component)
     * @param rabbitTemplate
     */
    public RabbitmqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
