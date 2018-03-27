package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SampleSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    public SampleSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * client send to /broadcastTest
     * client need to be registed to /topic/broadcastTest
     */
    @MessageMapping("/broadcastTest")
    @SendTo({"/topic/broadcastTest"})
    public String broadcastUrl(Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();
        System.out.println("msg receive from : "+userLogin+", msg : " + dataSendByClient);
        return "hello " + userLogin;
    }

    /**
     * client send to /pseudoSoloTest/{id}
     * client need to be registed to /topic/pseudoSoloTest/{id}
     */
    @MessageMapping("/pseudoSoloTest/{id}")
    @SendTo({"/topic/pseudoSoloTest/{id}"})
    public String unicastWithChannelNameInfoController(Principal principal, @DestinationVariable("id") final String id, String dataSendByClient) {
        String userLogin = principal.getName();

        System.out.println("User : "+userLogin+", id from UnicastWithPathChannelController :"+id);
        return id;
    }


    /**
     * client need to be authenticated
     * client send to /realUnicast
     * client need to be registed to /userLogin/realUnicast
     */
    @MessageMapping("/realUnicast")
    public void unicastWithAuthenticationController(Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        System.out.println("msg send to realUnicast controller from user : "+userLogin+", msg send "+dataSendByClient);

        //TODO replace userLogin by the user you want to contact
        simpMessagingTemplate.convertAndSendToUser(userLogin,"/realUnicast","msgToSend");
    }
}

