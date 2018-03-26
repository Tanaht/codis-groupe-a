package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Controller for intervention basic routes
 * /app/interventions/...
 */
@Controller
public class InterventionSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Constructor of the class {@link InterventionSocketController}.
     *
     * @param simpMessagingTemplate Template of the web socket
     */
    public InterventionSocketController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Choose intervention.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     * @return the string
     */
    @MessageMapping("/app/interventions/{id}/choose")
    @SendTo({"/topic/users/{username}/intervention-chosen"})
    public String chooseIntervention(@DestinationVariable("id") final String id,
                                     Principal principal,
                                     String dataSentByClient) {
        return "";
    }

    /**
     * Create intervention.
     *
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     * @return the string
     */
    @MessageMapping("/app/interventions/create")
    @SendTo({"/topic/interventions/created"})
    public String createIntervention(Principal principal, String dataSentByClient) {
        return "";
    }

    /**
     * Close intervention.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     * @return the string
     */
    @MessageMapping("/app/interventions/{id}/close")
    @SendTo({"/topic/interventions/closed"})
    public String closeIntervention(@DestinationVariable("id") final String id,
                                    Principal principal,
                                    String dataSentByClient) {
        return "";
    }
}
