package fr.istic.sit.codisgroupea.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqConsumer {

    @RabbitListener(queues = {RabbitmqConstante.queueTestFanout})
    public void receiveMessageFromFanout1(byte[] message) {
        System.out.println(new String(message));
    }
}
