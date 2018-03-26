package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Controller for symbol routes.
 */
@Controller
public class SymbolSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    public SymbolSocketController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Method to create new symbol when the client ask to.
     */
    @MessageMapping("/app/interventions/{id}/symbols/create")
    @SendTo({"/topic/interventions/{id}/symbols/event"})
    public String createSymbols(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     * Method to delete symbol when the client ask to.
     */
    @MessageMapping("/app/interventions/{id}/symbols/delete")
    @SendTo({"/topic/interventions/{id}/symbols/event"})
    public String deleteSymbols(@DestinationVariable("id") final String id, @DestinationVariable("id_symb") final String id_symb, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     * Method to update symbol when the client ask to.
     */
    @MessageMapping("/app/interventions/{id}/symbols/update")
    @SendTo({"/topic/interventions/{id}/symbols/event"})
    public String updateSymbols(@DestinationVariable("id") final String id, @DestinationVariable("id_symb") final String id_symb, Principal principal, String dataSendByClient) {
        return "";
    }
}
