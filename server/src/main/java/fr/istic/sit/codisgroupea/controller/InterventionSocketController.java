package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Controller for intervention basic routes
 * interventions/...
 */
@Controller
public class InterventionSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Constructor of the class {@link InterventionSocketController}
     * @param simpMessagingTemplate Template of the web socket
     */
    public InterventionSocketController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     *
     */
    @MessageMapping("/interventions/{id}/choose")
    @SendTo({"/interventions/{id}"})
    public String chooseIntervention(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping("/interventions/{create}")
    @SendTo({"/users/{username}/ack/intervention", "/interventions/update"})
    public String createIntervention(@DestinationVariable("create") final String create, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"/interventions/update"})
    public String updateIntervention(Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"/interventions/{id}"})
    public String detailIntervention(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }
}
