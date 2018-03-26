package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Controller for symbol routes
 * interventions/{id}/symbols/...
 */
@Controller
public class SymbolSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    public SymbolSocketController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"interventions/{id}/symbols/event"})
    public String eventSymbols(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"interventions/{id}/symbols/create"})
    public String createSymbols(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"interventions/{id}/symbols/{id_symb}/delete"})
    public String deleteSymbols(@DestinationVariable("id") final String id, @DestinationVariable("id_symb") final String id_symb, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"interventions/{id}/symbols/{id_symb}/update"})
    public String updateSymbols(@DestinationVariable("id") final String id, @DestinationVariable("id_symb") final String id_symb, Principal principal, String dataSendByClient) {
        return "";
    }
}
