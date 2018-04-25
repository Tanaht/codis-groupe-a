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

@Controller
public class DronePositionController {

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(DronePositionController.class);

    private SocketForDroneCommunication socketForDroneCommunication;

    /**
     * Constructor of the class {@link DronePositionController}
     *
     * @param socketForDroneCommunication
     */
    public DronePositionController(SocketForDroneCommunication socketForDroneCommunication) {
        this.socketForDroneCommunication = socketForDroneCommunication;
    }

    @MessageMapping(RoutesConfig.RECEIVE_DRONE_MISSION)
    public void getMission(@DestinationVariable("id") final int id, MissionOrderMessage missionOrder) {
        System.out.println("Mission order received !");
        System.out.println("For intervention "+missionOrder.getType()+" !");
        List<Location> path = new ArrayList<>();
        for(MissionOrderMessage.Location loc : missionOrder.getPath()){
            path.add(new Location(loc.getLat(), loc.getLng()));
        }
        socketForDroneCommunication.sendMessage(new MissionOrder("ASSIGN_MISSION", id, missionOrder.getType(), path));
    }
}