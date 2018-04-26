package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.exception.InvalidMessageException;
import fr.istic.sit.codisgroupea.service.UnitFactory;
import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Unit;
import fr.istic.sit.codisgroupea.model.message.UnitMessage;
import fr.istic.sit.codisgroupea.model.message.demand.CreateUnitMessage;
import fr.istic.sit.codisgroupea.model.message.demand.UnitCreatedMessage;
import fr.istic.sit.codisgroupea.model.message.receive.ConfirmDemandVehicleMessage;
import fr.istic.sit.codisgroupea.model.message.send.DemandesCreatedMessage;
import fr.istic.sit.codisgroupea.repository.InterventionRepository;
import fr.istic.sit.codisgroupea.repository.SymbolRepository;
import fr.istic.sit.codisgroupea.repository.UnitRepository;
import fr.istic.sit.codisgroupea.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static fr.istic.sit.codisgroupea.config.RoutesConfig.DENY_DEMAND_SERVER_CLIENT;

/**
 * Controller for demand routes.
 */
@Controller
@SuppressWarnings({"squid:S1192", "squid:S2629"})
public class DemandSocketController {

    private static final Logger logger = LoggerFactory.getLogger(DemandSocketController.class);

    /** Template of the web socket */
    private SimpMessagingTemplate simpMessagingTemplate;

    /** {@link UnitRepository} instance */
    private UnitRepository unitRepository;

    /** {@link InterventionRepository} instance */
    private InterventionRepository interventionRepository;

    /** {@link VehicleRepository} instance */
    private VehicleRepository vehicleRepository;

    /** {@link SymbolRepository} instance */
    private SymbolRepository symbolRepository;

    /** {@link UnitFactory} the unit factory from the DI */
    private UnitFactory unitFactory;

    /**
     * Constructor of the class {@link DemandSocketController}
     * @param simpMessagingTemplate Template of the web socket
     * @param unitRepository {@link UnitRepository} instance
     * @param interventionRepository {@link InterventionRepository} instance
     * @param vehicleRepository {@link VehicleRepository} instance
     * @param symbolRepository {@link SymbolRepository} instance
     * @Param unitFactory {@link UnitFactory} instance
     */
    public DemandSocketController(SimpMessagingTemplate simpMessagingTemplate, UnitRepository unitRepository, InterventionRepository interventionRepository, VehicleRepository vehicleRepository, SymbolRepository symbolRepository, UnitFactory unitFactory) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.unitRepository = unitRepository;
        this.interventionRepository = interventionRepository;
        this.vehicleRepository = vehicleRepository;
        this.symbolRepository = symbolRepository;
        this.unitFactory = unitFactory;
    }

    /**
     * Method to create a unit demand.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CREATE_UNIT_CLIENT)
    public void createUnit(@DestinationVariable("id") final int id, CreateUnitMessage dataSendByClient) {
        logger.trace("{} --> data receive {}", RoutesConfig.CREATE_UNIT_CLIENT, dataSendByClient);
        Intervention intervention = interventionRepository.getOne(id);


        try {
            Unit unit = unitFactory.createUnit(intervention, dataSendByClient);
            unit = unitRepository.save(unit);

            UnitCreatedMessage unitCreated = new UnitCreatedMessage(
                    unit.getId(),
                    new UnitCreatedMessage.Vehicle(dataSendByClient.vehicle.type)
            );

            Gson jason = new Gson();

            String toJson = jason.toJson(unitCreated);
            String urlToSend = RoutesConfig.CREATE_UNIT_SERVER_CLIENT.replace("{id}", String.valueOf(id));
            logger.trace("{} --> data send {}", urlToSend, toJson);
            simpMessagingTemplate.convertAndSend(RoutesConfig.CREATE_UNIT_SERVER_CLIENT,toJson);

            toJson = jason.toJson(new DemandesCreatedMessage(unit));
            urlToSend = RoutesConfig.CREATE_UNIT_SERVER_CODIS;
            logger.trace("{} --> data send {}", urlToSend, toJson);
            simpMessagingTemplate.convertAndSend(urlToSend,toJson);
        } catch(InvalidMessageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to validate a client demand.
     *
     * @param idUnit               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CONFIRM_DEMAND_CLIENT)
    public void confirmDemand(@DestinationVariable("idUnit") final int idUnit, ConfirmDemandVehicleMessage dataSendByClient) {
        Gson jason = new Gson();
        logger.trace("{} --> data receive {}", RoutesConfig.CONFIRM_DEMAND_CLIENT, jason.toJson(dataSendByClient));

        Optional<Unit> unit = unitRepository.findById(idUnit);

        if (!unit.isPresent()){
            logger.error("Unit with id {} doesn't exist in bdd", idUnit);
        }


        UnitMessage unitToSend = new UnitMessage(unit.get());
        //Message for the codis
        simpMessagingTemplate.convertAndSend(RoutesConfig.CONFIRM_DEMAND_SERVER_CODIS,"ping");

        Gson gson = new Gson();

        String routeToSend = "/topic/interventions/" +unit.get().getIntervention().getId()+
                "/units/"+idUnit+"/accepted";

        String toJson = gson.toJson(unitToSend,UnitMessage.class);

        logger.trace("{} --> data send {}",routeToSend, toJson);
        //Message for the client
        simpMessagingTemplate.convertAndSend(routeToSend,toJson);
    }

    /**
     * Method to deny a client demand.
     *
     * @param idUnit               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.DENY_DEMAND_CLIENT)
    public void denyDemand(@DestinationVariable("idUnit") final int idUnit, String dataSendByClient) {
        logger.trace("{} --> data receive {}", RoutesConfig.DENY_DEMAND_CLIENT, dataSendByClient);
        Gson jason = new GsonBuilder().create();
        Optional<Unit> optionalUnit = unitRepository.findById(idUnit);

        if (!optionalUnit.isPresent()){
            logger.error("Unit with id {} doesn't exist in bdd", idUnit);
            return;
        }

        Unit unit = optionalUnit.get();


        String toSend = "PING";

        logger.trace("{} --> data send {}", RoutesConfig.DENY_DEMAND_SERVER_CODIS, toSend);


        String urlToSend = DENY_DEMAND_SERVER_CLIENT
                .replace("{id}", String.valueOf(unit.getIntervention().getId()))
                .replace("{idUnit}", String.valueOf(idUnit));

        logger.trace("{} --> data send {}", urlToSend, toSend);


        //Message for the codis
        simpMessagingTemplate.convertAndSend(RoutesConfig.DENY_DEMAND_SERVER_CODIS,toSend);

        //Message for the client
        simpMessagingTemplate.convertAndSend(urlToSend,toSend);
        unitRepository.delete(unit);
    }
}
