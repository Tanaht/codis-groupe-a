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
    public void getInfoUser(Principal principal, @DestinationVariable("username") final String username, String dataSendByClient) {
        logger.info("msg receive from : "+principal.getName()+", msg : " + dataSendByClient);

        Optional<User> user = authenticationService.getUser(principal.getName());

        if (!user.isPresent()){
            logger.error(principal.getName() + " doesn't exist in the bdd");
        }

        Gson gson = new Gson();
        simpMessagingTemplate.convertAndSend("/topic/users/"+principal.getName()+"/initialize-application",gson.toJson(user.get(),User.class));
    }
}
