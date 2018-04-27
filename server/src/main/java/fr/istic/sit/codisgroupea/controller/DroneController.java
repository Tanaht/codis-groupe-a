package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.Path;
import fr.istic.sit.codisgroupea.model.entity.PathType;
import fr.istic.sit.codisgroupea.model.entity.Position;
import fr.istic.sit.codisgroupea.model.message.receive.MissionOrderMessage;
import fr.istic.sit.codisgroupea.repository.PathRepository;
import fr.istic.sit.codisgroupea.socket.Location;
import fr.istic.sit.codisgroupea.socket.MissionOrder;
import fr.istic.sit.codisgroupea.socket.SocketForDroneCommunication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller for drone actions
 */
@Controller
public class DroneController {

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(DroneController.class);

    private SimpMessagingTemplate simpMessagingTemplate;

    private PathRepository pathRepository;

    /**
     * The socket between server and drone {@link SocketForDroneCommunication}
     */
    private SocketForDroneCommunication socketForDroneCommunication;

    /**
     * Constructor of the class {@link DroneController}
     *
     * @param socketForDroneCommunication
     * @param simpMessagingTemplate
     * @param pathRepository
     */
    public DroneController(SocketForDroneCommunication socketForDroneCommunication,
                           SimpMessagingTemplate simpMessagingTemplate,
                           PathRepository pathRepository) {
        this.socketForDroneCommunication = socketForDroneCommunication;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.pathRepository = pathRepository;
    }

    /**
     * Create a path from the mission-order message and persist it.
     *
     * @param msg the base message
     * @return the persisted path
     */
    private Path missionOrderMessageToPath(MissionOrderMessage msg) {
        List<Position> points = new LinkedList<>();

        for(MissionOrderMessage.Location loc : msg.getPath()) {
            Position position = new Position(loc.getLat(), loc.getLng());
            points.add(position);
        }

        Path path = new Path(msg.getAltitude(), points, PathType.valueOf(msg.getType()));
        path = pathRepository.save(path);

        return path;
    }

    /**
     * Catch a new mission order from the android
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

        missionOrderMessageToPath(missionOrder);

        logger.info("Link mission to drone using SocketForDroneCommunication");
        Gson gson = new Gson();
        String toJson = gson.toJson(missionOrder);
        String urlToSend = RoutesConfig.RECEIVE_DRONE_MISSION_PART1+id+RoutesConfig.RECEIVE_DRONE_MISSION_PART2;
        logger.info("{} --> data send {}",urlToSend, toJson);
        simpMessagingTemplate.convertAndSend(urlToSend, toJson);
        socketForDroneCommunication.sendMessage(new MissionOrder("ASSIGN_MISSION", id, missionOrder.getType(), path));
    }
}