package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.ListUnitMessage;
import fr.istic.sit.codisgroupea.model.message.Receive.ConfirmDemandVehicleMessage;
import fr.istic.sit.codisgroupea.model.message.Send.DemandesCreatedMessage;
import fr.istic.sit.codisgroupea.model.message.UnitMessage;
import fr.istic.sit.codisgroupea.model.message.VehicleMessage;
import fr.istic.sit.codisgroupea.model.message.demand.CreateUnitMessage;
import fr.istic.sit.codisgroupea.model.message.demand.UnitCreatedMessage;
import fr.istic.sit.codisgroupea.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller for demand routes.
 */
@Controller
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

    /** {@link DefaultVehicleSymbolRepository} instance */
    private DefaultVehicleSymbolRepository defaultVehicleSymbolRepository;

    /**
     * Constructor of the class {@link DemandSocketController}
     * @param simpMessagingTemplate Template of the web socket
     * @param unitRepository {@link UnitRepository} instance
     * @param interventionRepository {@link InterventionRepository} instance
     * @param vehicleRepository {@link VehicleRepository} instance
     * @param symbolRepository {@link SymbolRepository} instance
     */
    public DemandSocketController(SimpMessagingTemplate simpMessagingTemplate, UnitRepository unitRepository, InterventionRepository interventionRepository, VehicleRepository vehicleRepository, SymbolRepository symbolRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.unitRepository = unitRepository;
        this.interventionRepository = interventionRepository;
        this.vehicleRepository = vehicleRepository;
        this.symbolRepository = symbolRepository;
    }

    /**
     * Method to create a unit demand.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CREATE_UNIT_CLIENT)
    public void createUnit(@DestinationVariable("id") final int id, Principal principal, CreateUnitMessage dataSendByClient) {
        logger.trace(RoutesConfig.CREATE_UNIT_CLIENT+" --> data receive "+dataSendByClient);

        Intervention intervention = interventionRepository.getOne(id);
        String userLogin = principal.getName();
        Timestamp now = new Timestamp(new Date().getTime());

        Symbol symbol = defaultVehicleSymbolRepository
                .findByType(new VehicleType(dataSendByClient.vehicle.type))
                .getSymbol();

        SymbolSitac symbolSitac = new SymbolSitac(
                intervention,
                symbol,
                null,
                new Payload(null, null)
        );

        //Message for the codis
        Unit unit = new Unit(
                intervention,
                null,
                true,
                now,
                new Timestamp(0),
                symbolSitac
        );

        unit = unitRepository.save(unit);

        UnitCreatedMessage unitCreated = new UnitCreatedMessage(
                unit.getId(),
                new UnitCreatedMessage.Vehicle(dataSendByClient.vehicle.type)
        );

        Gson jason = new Gson();

        //Message for the client
        String toJson = jason.toJson(unitCreated);
        String urlToSend = RoutesConfig.CREATE_UNIT_SERVER_CLIENT;
        logger.trace(urlToSend+" --> data send "+toJson);
        simpMessagingTemplate.convertAndSend(RoutesConfig.CREATE_UNIT_SERVER_CLIENT,toJson);

        toJson = jason.toJson(new DemandesCreatedMessage(unit));
        urlToSend = RoutesConfig.CREATE_UNIT_SERVER_CODIS;
        logger.trace(urlToSend+" --> data send "+toJson);
        simpMessagingTemplate.convertAndSend(urlToSend,toJson);
    }

    /**
     * Method to update a unit.
     *
     * @param idInterventions  the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.UPDATE_UNIT_CLIENT)
    @SendTo({RoutesConfig.UPDATE_UNIT_SERVER})
    public ListUnitMessage updateUnit(@DestinationVariable("id") final int idInterventions, Principal principal, List<UnitMessage> dataSendByClient) {
        Gson jason = new Gson();
        logger.trace(RoutesConfig.UPDATE_UNIT_CLIENT +" --> data receive "+jason.toJson(dataSendByClient));

        Optional<Intervention> intervention = interventionRepository.findById(idInterventions);

        if (!intervention.isPresent()){
            logger.error("Intervention with id "+ idInterventions+" doesn't exist");
        }

        List<UnitMessage> listUnitUpdated = new ArrayList<>();

        for (UnitMessage unitMessageFromCLient : dataSendByClient){
            Optional<Unit> unitInBdd = unitRepository.findById(unitMessageFromCLient.getId());
            Optional<Vehicle> vehicle = vehicleRepository.findVehicleByLabel(unitMessageFromCLient.getVehicule().getLabel());
            Optional<Symbol> symb = symbolRepository
                    .findSymbolByColorAndShape(unitMessageFromCLient.getSymbolUnitMessage().getColor(),
                            unitMessageFromCLient.getSymbolUnitMessage().getShape());

            if(!unitInBdd.isPresent()){
                logger.error("Unit with id "+unitMessageFromCLient.getId()+" doesn't exist in bdd");
            }

            if(!vehicle.isPresent()){
                logger.error("Vehicle with label " + unitMessageFromCLient.getVehicule().getLabel() +
                        " doesn't exist in bdd");
            }

            if(!symb.isPresent()){
                logger.error("sym with color " + unitMessageFromCLient.getSymbolUnitMessage().getColor() +
                        " and shape " + unitMessageFromCLient.getSymbolUnitMessage().getShape() + "doesn't exist");
            }


            unitInBdd.get().getSymbolSitac().setSymbol(symb.get());
            unitInBdd.get().getSymbolSitac().setLocation(unitMessageFromCLient
                    .getSymbolUnitMessage().getLocation().toPositionEntity());

            vehicle.get().setStatus(unitMessageFromCLient.getVehicule().getStatus());

            unitInBdd.get().setVehicle(vehicle.get());
            unitInBdd.get().setMoving(unitMessageFromCLient.isMoving());

            unitInBdd.get().setAcceptDate(new Timestamp(unitMessageFromCLient.getDate_accepted()));
            unitInBdd.get().setRequestDate(new Timestamp(unitMessageFromCLient.getDate_granted()));

            unitRepository.save(unitInBdd.get());
            UnitMessage unitUpdated = new UnitMessage(unitInBdd.get());

            listUnitUpdated.add(unitUpdated);
        }

        ListUnitMessage toReturn = new ListUnitMessage("UPDATE", listUnitUpdated);
        logger.trace(RoutesConfig.UPDATE_UNIT_SERVER+" --> data send "+jason.toJson(toReturn));
        return toReturn;
    }

    /**
     * Method to validate a client demand.
     *
     * @param idUnit               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CONFIRM_DEMAND_CLIENT)
    public void confirmDemand(@DestinationVariable("idUnit") final int idUnit, Principal principal, ConfirmDemandVehicleMessage dataSendByClient) {
        Gson jason = new Gson();
        logger.trace(RoutesConfig.CONFIRM_DEMAND_CLIENT +" --> data receive "+jason.toJson(dataSendByClient));

        String userLogin = principal.getName();
        Optional<Unit> unit = unitRepository.findById(idUnit);
        if (!unit.isPresent()){
            logger.error("Unit with id "+idUnit+" doesn't exist in bdd");
        }


        UnitMessage unitToSend = new UnitMessage(unit.get());
        //Message for the codis
        simpMessagingTemplate.convertAndSend(RoutesConfig.CONFIRM_DEMAND_SERVER_CODIS,"ping");

        Gson gson = new Gson();

        String routeToSend = "/topic/interventions/" +unit.get().getIntervention().getId()+
                "/units/"+idUnit+"/accepted";

        String toJson = gson.toJson(unitToSend,UnitMessage.class);

        logger.trace(routeToSend+" --> data send "+toJson);
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
    public void denyDemand(@DestinationVariable("idUnit") final int idUnit, Principal principal, String dataSendByClient) {
        Gson jason = new Gson();
        logger.trace(RoutesConfig.DENY_DEMAND_CLIENT +" --> data receive "+dataSendByClient);

        String userLogin = principal.getName();
        Optional<Unit> unit = unitRepository.findById(idUnit);

        if (!unit.isPresent()){
            logger.error("Unit with id "+idUnit+" doesn't exist in bdd");
        }


        String toSend = "denied";
        logger.trace(RoutesConfig.DENY_DEMAND_SERVER_CODIS+" --> data send "+toSend);
        //Message for the codis
        simpMessagingTemplate.convertAndSend(RoutesConfig.DENY_DEMAND_SERVER_CODIS,toSend);

        toSend = "denied";
        String urlToSend = "/topic/interventions/"+
                unit.get().getIntervention().getId()+"/units/"+idUnit+"/denied";
        logger.trace(urlToSend+" --> data send "+toSend);
        //Message for the client
        simpMessagingTemplate.convertAndSend(urlToSend,toSend);
    }
}
