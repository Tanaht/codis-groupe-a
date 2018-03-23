package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.model.entity.User;
import fr.istic.sit.codisgroupea.service.AuthenticationService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private SimpMessagingTemplate simpMessagingTemplate;

    public AuthenticationController(AuthenticationService authenticationService, SimpMessagingTemplate simpMessagingTemplate) {
        this.authenticationService = authenticationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    /**
     *
     * @param username
     * @param dataSendByClient
     * @return
     */
    @MessageMapping("/users/{username}/subscribed")
    public void getInfoUser(@DestinationVariable("username") final String username, String dataSendByClient) {
        System.out.println("msg receive from : "+username+", msg : " + dataSendByClient);

        Optional<User> user = authenticationService.getUser(username);

        System.out.println("send to /interventions/retrieve");
        simpMessagingTemplate.convertAndSend("/topic/interventions/retrieve","retrieve");
        System.out.println("send to /users/"+username+"");
        simpMessagingTemplate.convertAndSend("/topic/users/"+username+"","msg");

    }
}
