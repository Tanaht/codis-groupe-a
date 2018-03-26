package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class UserSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    public UserSocketController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"users/{username}"})
    public String detailUser(@DestinationVariable("username") final String username, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping("users/{username}/subscribed")
    public String subscribeUser(Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"users/{username}/ack/intervention"})
    public String ackIntervention(@DestinationVariable("username") final String username, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"users/{username}/ack/demande"})
    public String ackDemande(@DestinationVariable("username") final String username, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     *
     */
    @MessageMapping()
    @SendTo({"users/{username}/ack/symbol"})
    public String ackSymbol(@DestinationVariable("username") final String username, Principal principal, String dataSendByClient) {
        return "";
    }
}
