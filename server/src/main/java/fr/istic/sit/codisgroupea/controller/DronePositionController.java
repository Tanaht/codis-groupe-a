package fr.istic.sit.codisgroupea.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.message.LocationMessage;
import fr.istic.sit.codisgroupea.socket.Location;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class DronePositionController {

    /** Template of the web socket  */
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Constructor of the class {@link DronePositionController}
     *
     * @param simpMessagingTemplate Template of the web socket
     */
    public DronePositionController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    public void sendDronePosition(Location location) {
        Gson gson = new Gson();
        String toJson = gson.toJson(new LocationMessage(location.getLat(), location.getLng(), location.getAlt()),LocationMessage.class);
        simpMessagingTemplate.convertAndSend(RoutesConfig.SEND_DRONE_POSITION_PART1+location.getInterventionId()+RoutesConfig.SEND_DRONE_POSITION_PART2, toJson);
    }
}
