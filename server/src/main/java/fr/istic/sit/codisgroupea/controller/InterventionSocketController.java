package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
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

    /** Template of the web socket */
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
    @MessageMapping(RoutesConfig.CHOOSE_INTERVENTION_CLIENT)
    @SendTo({RoutesConfig.CHOOSE_INTERVENTION_SERVER})
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
    @MessageMapping(RoutesConfig.CREATE_INTERVENTION_CLIENT)
    @SendTo({RoutesConfig.CREATE_INTERVENTION_SERVER})
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
    @MessageMapping(RoutesConfig.CLOSE_INTERVENTION_CLIENT)
    @SendTo({RoutesConfig.CLOSE_INTERVENTION_SERVER})
    public String closeIntervention(@DestinationVariable("id") final String id,
                                    Principal principal,
                                    String dataSentByClient) {
        return "";
    }
}
