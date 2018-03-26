package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
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

    /** Template of the web socket */
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Constructor of the class {@link SymbolSocketController}
     * @param simpMessagingTemplate Template of the web socket
     */
    public SymbolSocketController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Method to create new symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.CREATE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.CREATE_SYMBOL_SERVER})
    public String createSymbols(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     * Method to delete symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.DELETE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.DELETE_SYMBOL_SERVER})
    public String deleteSymbols(@DestinationVariable("id") final String id, @DestinationVariable("id_symb") final String id_symb, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     * Method to update symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.UPDATE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.UPDATE_SYMBOL_SERVER})
    public String updateSymbols(@DestinationVariable("id") final String id, @DestinationVariable("id_symb") final String id_symb, Principal principal, String dataSendByClient) {
        return "";
    }
}
