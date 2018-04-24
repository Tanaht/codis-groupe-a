package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.message.receive.MissionOrderMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class DronePositionController {

    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Constructor of the class {@link DronePositionController}
     *
     * @param simpMessagingTemplate Template of the web socket
     */
    public DronePositionController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping(RoutesConfig.RECEIVE_DRONE_MISSION)
    public void getMission(@DestinationVariable("id") final int id, MissionOrderMessage missionOrder) {
        System.out.println("Mission order received !");
        System.out.println("For intervention "+missionOrder.getType()+" !");
    }
}