package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.model.entity.User;
import fr.istic.sit.codisgroupea.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

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

        if (!user.isPresent()){
            logger.error(username + " doesn't exist in the bdd");
        }

        Gson gson = new Gson();
        simpMessagingTemplate.convertAndSend("/topic/users/"+username+"/initialize-application",gson.toJson(user.get(),User.class));
    }
}
