package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Controller for drone routes
 * drone/...
 * interventions/drone/...
 */
@Controller
public class DroneSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    public DroneSocketController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"interventions/{id}/drone/photos/create"})
    public String createPhotoDrone(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }
}
