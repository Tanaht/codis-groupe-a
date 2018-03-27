package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Symbol;
import fr.istic.sit.codisgroupea.model.entity.Unit;
import fr.istic.sit.codisgroupea.model.entity.Vehicle;
import fr.istic.sit.codisgroupea.model.message.UnitMessage;
import fr.istic.sit.codisgroupea.repository.InterventionRepository;
import fr.istic.sit.codisgroupea.repository.SymbolRepository;
import fr.istic.sit.codisgroupea.repository.UnitRepository;
import fr.istic.sit.codisgroupea.repository.VehicleRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.Optional;

/**
 * Controller for demand routes.
 */
@Controller
public class DemandSocketController {

    /** Template of the web socket */
    private SimpMessagingTemplate simpMessagingTemplate;

    private UnitRepository unitRepository;
    private InterventionRepository interventionRepository;
    private VehicleRepository vehicleRepository;
    private SymbolRepository symbolRepository;

    /**
     * Constructor of the class {@link DemandSocketController}
     * @param simpMessagingTemplate Template of the web socket
     * @param unitRepository
     * @param interventionRepository
     * @param vehicleRepository
     * @param symbolRepository
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
    public void createUnit(@DestinationVariable("id") final String id, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.CREATE_UNIT_SERVER_CODIS,"msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.CREATE_UNIT_SERVER_CLIENT,"msgToSend");
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
    public String updateUnit(@DestinationVariable("id") final long idInterventions, Principal principal, UnitMessage dataSendByClient) {
        Optional<Intervention> intervention = interventionRepository.findById(idInterventions);
        Optional<Unit> unitInBdd = unitRepository.findById(dataSendByClient.getId());
        Optional<Vehicle> vehicle = vehicleRepository.findVehicleByLabel(dataSendByClient.getVehicule().getLabel());



        return "";
    }

    /**
     * Method to validate a client demand.
     *
     * @param idUnit               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CONFIRM_DEMAND_CLIENT)
    public void confirmDemand(@DestinationVariable("idUnit") final String idUnit, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.CONFIRM_DEMAND_SERVER_CODIS,"msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.CONFIRM_DEMAND_SERVER_CLIENT,"msgToSend");
    }

    /**
     * Method to deny a client demand.
     *
     * @param idUnit               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.DENY_DEMAND_CLIENT)
    public void denyDemand(@DestinationVariable("idUnit") final String idUnit, Principal principal, String dataSendByClient) {
        String userLogin = principal.getName();

        //Message for the codis
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.DENY_DEMAND_SERVER_CODIS,"msgToSend");

        //Message for the client
        simpMessagingTemplate.convertAndSendToUser(userLogin, RoutesConfig.DENY_DEMAND_SERVER_CLIENT,"msgToSend");
    }
}
