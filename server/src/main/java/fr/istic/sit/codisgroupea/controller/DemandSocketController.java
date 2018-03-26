package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Controller for demand routes.
 */
@Controller
public class DemandSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    public DemandSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Method to create a unit demand.
     */
    @MessageMapping("/app/interventions/{id}/units/create")
    public void createUnit(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin,"/topic/demandes/created","msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin,"/topic/interventions/{id}/units/event","msgToSend");
    }

    /**
     * Method to update a unit.
     */
    @MessageMapping("/app/interventions/{id}/units/update")
    @SendTo({"/topic/interventions/{id}/units/event"})
    public String updateUnit(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     * Method to validate a client demand.
     */
    @MessageMapping("/app/demandes/{idUnit}/accept")
    public void confirmDemand(@DestinationVariable("idUnit") final String idUnit, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin,"/topic/demandes/{id}/accepted","msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin,"/topic/interventions/{id}/units/{id}/accepted","msgToSend");
    }

    /**
     * Method to deny a client demand.
     */
    @MessageMapping("/app/demandes/{idUnit}/deny")
    public void denyDemand(@DestinationVariable("idUnit") final String idUnit, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin,"/topic/demandes/{id}/denied","msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin,"/topic/interventions/{id}/units/{id}/denied","msgToSend");
    }
}
