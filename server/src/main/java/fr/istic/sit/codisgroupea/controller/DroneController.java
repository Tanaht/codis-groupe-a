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
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for drone actions
 */
@Controller
public class DroneController {

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(DroneController.class);

    /**
     * The socket between server and drone {@link SocketForDroneCommunication}
     */
    private SocketForDroneCommunication socketForDroneCommunication;

    /**
     * Constructor of the class {@link DroneController}
     *
     * @param socketForDroneCommunication
     */
    public DroneController(SocketForDroneCommunication socketForDroneCommunication) {
        this.socketForDroneCommunication = socketForDroneCommunication;
    }

    /**
     * Catch a new mission order from the drone
     * @param id
     * @param missionOrder
     */
    @MessageMapping(RoutesConfig.RECEIVE_DRONE_MISSION)
    public void getMission(@DestinationVariable("id") final int id, MissionOrderMessage missionOrder) {
        logger.info("Mission order received from drone");
        List<Location> path = new ArrayList<>();
        for(MissionOrderMessage.Location loc : missionOrder.getPath()){
            path.add(new Location(loc.getLat(), loc.getLng()));
        }
        logger.info("Link mission to drone using SocketForDroneCommunication");
        socketForDroneCommunication.sendMessage(new MissionOrder("ASSIGN_MISSION", id, missionOrder.getType(), path));
    }
}