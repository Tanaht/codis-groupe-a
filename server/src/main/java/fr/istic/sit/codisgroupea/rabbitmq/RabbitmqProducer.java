package fr.istic.sit.codisgroupea.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqProducer implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public RabbitmqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i <100 ; i++) {
            rabbitTemplate.convertAndSend(RabbitmqConstante.topicExchangeName,RabbitmqConstante.queueTestFanout, "msg test");
            Thread.sleep(3000);
        }
    }
}
