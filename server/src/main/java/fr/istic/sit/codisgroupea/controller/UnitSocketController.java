package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Symbol;
import fr.istic.sit.codisgroupea.model.entity.Unit;
import fr.istic.sit.codisgroupea.model.entity.Vehicle;
import fr.istic.sit.codisgroupea.model.message.ListUnitMessage;
import fr.istic.sit.codisgroupea.model.message.UnitMessage;
import fr.istic.sit.codisgroupea.repository.InterventionRepository;
import fr.istic.sit.codisgroupea.repository.SymbolRepository;
import fr.istic.sit.codisgroupea.repository.UnitRepository;
import fr.istic.sit.codisgroupea.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * WebSocket Controller intended to manage the states of the units
 */
@Controller
public class UnitSocketController {


    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(SymbolSocketController.class);

    /** {@link InterventionRepository} instance */
    private InterventionRepository interventionRepository;

    /** {@link UnitRepository instance to request the database */
    private UnitRepository unitRepository;

    /** {@link VehicleRepository} instance from the DI */
    private VehicleRepository vehicleRepository;

    /** {@link SymbolRepository} instance */
    private SymbolRepository symbolRepository;

    /**
     * Instantiates a new Unit socket controller.
     *
     * @param interventionRepository the intervention repository
     * @param unitRepository         the unit repository
     */
    public UnitSocketController(InterventionRepository interventionRepository,
                                UnitRepository unitRepository,
                                VehicleRepository vehicleRepository,
                                SymbolRepository symbolRepository) {
        this.interventionRepository = interventionRepository;
        this.unitRepository = unitRepository;
        this.vehicleRepository = vehicleRepository;
        this.symbolRepository = symbolRepository;
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
        logger.trace("{} --> data receive {}", RoutesConfig.UPDATE_UNIT_CLIENT, jason.toJson(dataSendByClient));

        Optional<Intervention> intervention = interventionRepository.findById(idInterventions);

        if (!intervention.isPresent()){
            logger.error("Intervention with id {} doesn't exist", idInterventions);
        }

        List<UnitMessage> listUnitUpdated = new ArrayList<>();

        for (UnitMessage unitMessageFromCLient : dataSendByClient){
            Optional<Unit> unitInBdd = unitRepository.findById(unitMessageFromCLient.getId());
            Optional<Vehicle> vehicle = vehicleRepository.findVehicleByLabel(unitMessageFromCLient.getVehicule().getLabel());
            Optional<Symbol> symb = symbolRepository
                    .findSymbolByColorAndShape(unitMessageFromCLient.getSymbolUnitMessage().getColor(),
                            unitMessageFromCLient.getSymbolUnitMessage().getShape());

            if(!unitInBdd.isPresent()){
                logger.error("Unit with id {} doesn't exist in bdd", unitMessageFromCLient.getId());
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

//TODO:            vehicle.get().setStatus(unitMessageFromCLient.getVehicule().getStatus());

            unitInBdd.get().setVehicle(vehicle.get());
            unitInBdd.get().setMoving(unitMessageFromCLient.isMoving());

            unitInBdd.get().setAcceptDate(new Timestamp(unitMessageFromCLient.getDate_accepted()));
            unitInBdd.get().setRequestDate(new Timestamp(unitMessageFromCLient.getDate_granted()));

            unitRepository.save(unitInBdd.get());
            UnitMessage unitUpdated = new UnitMessage(unitInBdd.get());

            listUnitUpdated.add(unitUpdated);
        }

        //FIXME: Ask question to beaulieu: I have difficulty to understand the reason of the object return
        ListUnitMessage toReturn = new ListUnitMessage("UPDATE", listUnitUpdated);
        String json = jason.toJson(toReturn);
        logger.trace("{} --> data send {}", RoutesConfig.UPDATE_UNIT_SERVER, json);
        return toReturn;
    }
}
