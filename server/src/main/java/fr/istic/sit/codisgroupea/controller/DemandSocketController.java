package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
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

    /** Template of the web socket */
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Constructor of the class {@link DemandSocketController}
     * @param simpMessagingTemplate Template of the web socket
     */
    public DemandSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Method to create a unit demand.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CREATE_UNIT_CLIENT)
    public void createUnit(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.CREATE_UNIT_SERVER_CODIS,"msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.CREATE_UNIT_SERVER_CLIENT,"msgToSend");
    }

    /**
     * Method to update a unit.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.UPDATE_UNIT_CLIENT)
    @SendTo({RoutesConfig.UPDATE_UNIT_SERVER})
    public String updateUnit(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     * Method to validate a client demand.
     *
     * @param idUnit               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CONFIRM_DEMAND_CLIENT)
    public void confirmDemand(@DestinationVariable("idUnit") final String idUnit, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.CONFIRM_DEMAND_SERVER_CODIS,"msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.CONFIRM_DEMAND_SERVER_CLIENT,"msgToSend");
    }

    /**
     * Method to deny a client demand.
     *
     * @param idUnit               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.DENY_DEMAND_CLIENT)
    public void denyDemand(@DestinationVariable("idUnit") final String idUnit, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.DENY_DEMAND_SERVER_CODIS,"msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.DENY_DEMAND_SERVER_CLIENT,"msgToSend");
    }
}
