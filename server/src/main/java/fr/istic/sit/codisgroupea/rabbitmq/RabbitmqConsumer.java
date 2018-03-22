package fr.istic.sit.codisgroupea.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * liste de tout les listener rabbitmq
 */
@Component
public class RabbitmqConsumer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * don't use (spring component)
     * @param rabbitTemplate
     */
    public RabbitmqConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    /**
     * test listener
     * @param message receive
     */
    @RabbitListener(queues = {RabbitmqConstante.queueTestFanout})
    public void receiveMessageFromFanout1(byte[] message) {
        System.out.println(new String(message));

        rabbitTemplate.convertAndSend(RabbitmqConstante.topicExchangeName,RabbitmqConstante.queueTestFanout, "prout");
    }

}
