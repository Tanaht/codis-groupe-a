package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Path;
import fr.istic.sit.codisgroupea.model.entity.PathType;
import fr.istic.sit.codisgroupea.model.entity.Position;
import fr.istic.sit.codisgroupea.model.message.receive.MissionOrderMessage;
import fr.istic.sit.codisgroupea.repository.InterventionRepository;
import fr.istic.sit.codisgroupea.repository.PathRepository;
import fr.istic.sit.codisgroupea.socket.Location;
import fr.istic.sit.codisgroupea.socket.MissionOrder;
import fr.istic.sit.codisgroupea.socket.SocketForDroneCommunication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for drone actions
 */
@Controller
@Transactional
public class DroneController {

    /** The logger */
    private static final Logger logger = LogManager.getLogger();

    private SimpMessagingTemplate simpMessagingTemplate;

    private InterventionRepository interventionRepository;

    private PathRepository pathRepository;

    /**
     * The socket between server and drone {@link SocketForDroneCommunication}
     */
    private SocketForDroneCommunication socketForDroneCommunication;

    /**
     * Constructor of the class {@link DroneController}
     * @param socketForDroneCommunication
     * @param simpMessagingTemplate
     * @param interventionRepository
     * @param pathRepository
     */
    public DroneController(SocketForDroneCommunication socketForDroneCommunication, SimpMessagingTemplate simpMessagingTemplate, InterventionRepository interventionRepository, PathRepository pathRepository) {
        this.socketForDroneCommunication = socketForDroneCommunication;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.interventionRepository = interventionRepository;
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

        for(MissionOrderMessage.Location loc : msg.getPoints()) {
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

        List<Position> lPosition = missionOrder.getPoints().stream()
                .map( e -> {
                    Position pos = new Position();
                    pos.setLatitude(e.getLat());
                    pos.setLongitude(e.getLng());
                    return pos;
                })
                .collect(Collectors.toList());
        Intervention interv = interventionRepository.getOneById(id);
        //TODO delete previous position in bdd
        if(interv.getPathDrone() == null) {
            Path newPath = new Path(missionOrder);
            interv.setPathDrone(newPath);
        }
        interv.getPathDrone().setPoints(lPosition);
        pathRepository.save(interv.getPathDrone());


        logger.info("Link mission to drone using SocketForDroneCommunication");
        Gson gson = new Gson();
        String toJson = gson.toJson(missionOrder);
        String urlToSend = RoutesConfig.RECEIVE_DRONE_MISSION_PART1+id+RoutesConfig.RECEIVE_DRONE_MISSION_PART2;
        logger.info("{} --> data send {}",urlToSend, toJson);
        simpMessagingTemplate.convertAndSend(urlToSend, toJson);

    }

    @MessageMapping(RoutesConfig.RECEIVE_ORDER_MISSION_DRONE)
    public void sendMission(@DestinationVariable("id") final int id){
        Intervention interv = interventionRepository.getOneById(id);

        List<Location> listPoints = interv.getPathDrone().getPoints().stream()
                .map(e->new Location(e,interv))
                .collect(Collectors.toList());

        socketForDroneCommunication.sendMessage(
                new MissionOrder("ASSIGN_MISSION",
                        interv.getId(),
                        interv.getPathDrone().getType().name(),
                        listPoints));
    }


}