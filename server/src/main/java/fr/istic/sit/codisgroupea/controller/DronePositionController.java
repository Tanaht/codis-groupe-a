package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.message.receive.MissionOrderMessage;
import fr.istic.sit.codisgroupea.socket.Location;
import fr.istic.sit.codisgroupea.socket.MissionOrder;
import fr.istic.sit.codisgroupea.socket.SocketForDroneCommunication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DronePositionController {

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(DronePositionController.class);

    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Constructor of the class {@link DronePositionController}
     *
     * @param simpMessagingTemplate Template of the web socket
     */
    public DronePositionController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping(RoutesConfig.RECEIVE_DRONE_MISSION)
    public void getMission(@DestinationVariable("id") final int id, MissionOrderMessage missionOrder) {
        System.out.println("Mission order received !");
        System.out.println("For intervention "+missionOrder.getType()+" !");
        SocketForDroneCommunication socket = null;
        try {
            socket = SocketForDroneCommunication.get();
            List<Location> path = new ArrayList<>();
            for(MissionOrderMessage.Location loc : missionOrder.getPath()){
                path.add(new Location(loc.getLat(), loc.getLng()));
            }
            socket.sendMessage(new MissionOrder("ASSIGN_MISSION", id, missionOrder.getType(), path));
        } catch (IOException e) {
            logger.error("Socket between server and drone cannot be instanciated. Please enabled debug mode for more details");
            if(logger.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
    }
}