package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.constraints.groups.Message;
import fr.istic.sit.codisgroupea.exception.InvalidMessageException;
import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Unit;
import fr.istic.sit.codisgroupea.model.message.ListUnitMessage;
import fr.istic.sit.codisgroupea.model.message.UnitMessage;
import fr.istic.sit.codisgroupea.repository.UnitRepository;
import fr.istic.sit.codisgroupea.service.UnitFactory;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.validation.Validator;
import java.util.*;

/**
 * WebSocket Controller intended to manage the states of the units
 */
@Controller
public class UnitSocketController {

    /**
     * The logger
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * {@link UnitRepository instance to request the database
     */
    private UnitRepository unitRepository;

    /**
     * {@link UnitFactory } instance
     */
    private UnitFactory unitFactory;

    /**
     * {@link Validator} instance
     */
    private Validator validator;

    /**
     * Instantiates a new Unit socket controller.
     *
     * @param unitRepository the unit repository
     * @param unitFactory    the unit factory
     * @param validator      the validator
     */
    public UnitSocketController(UnitRepository unitRepository,
                                UnitFactory unitFactory,
                                Validator validator) {
        this.unitRepository = unitRepository;
        this.unitFactory = unitFactory;
        this.validator = validator;
    }

    /**
     * Method to update a unit.
     *
     * @param idIntervention   the id
     * @param dataSentByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.UPDATE_UNIT_CLIENT)
    @SendTo({RoutesConfig.UPDATE_UNIT_SERVER})
    public ListUnitMessage updateUnit(
            @DestinationVariable("id") final int idIntervention,
            List<UnitMessage> dataSentByClient) {
        Gson jason = new GsonBuilder().create();
        logger.trace("{} --> data received {}",
                RoutesConfig.UPDATE_UNIT_CLIENT,
                jason.toJson(dataSentByClient));

        try {
            checkAccessViolations(dataSentByClient);

            // Transform the messages into valid units for persistence.
            List<Unit> toBePersisted = new ArrayList<>();
            for(val unitMessage : dataSentByClient) {
                persistUnit(toBePersisted, unitMessage);
            }

            unitRepository.saveAll(toBePersisted);

            // Transform the previously built units into messages.
            List<UnitMessage> unitMessages = new ArrayList<>();
            for(val unit : toBePersisted) {
                unitMessages.add(new UnitMessage(unit));
            }

            return new ListUnitMessage("UPDATE", unitMessages);
        } catch (InvalidMessageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return new ListUnitMessage("UPDATE", Collections.emptyList());
    }

    /**
     * Add the unit described by the message into the persistence list.
     *
     * @param persist     the persistence list
     * @param unitMessage the unit message
     */
    private void persistUnit(List<Unit> persist, UnitMessage unitMessage) {
        val optionalUnit = unitRepository.findOneById(
                unitMessage.getId());

        if (!optionalUnit.isPresent()) {
            val msg = "Cannot retrieve unit with ID {}";
            logger.error(msg, unitMessage.getId());
            return;
        }

        Unit unit = optionalUnit.get();
        if (persist.contains(unit)) {
            val msg = "Cannot update the same unit twice in a single message";
            logger.warn(msg);
            return;
        }

        try {
            unitFactory.updateUnit(unit, unitMessage);
            persist.add(unit);
        } catch (InvalidMessageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Check if no access has been violated.
     *
     * @param dataSentByClient         the data sent by the client
     * @throws InvalidMessageException thrown in case of access violation
     */
    private void checkAccessViolations(List<UnitMessage> dataSentByClient)
            throws InvalidMessageException {
        val violations = validator.validate(dataSentByClient,
                Message.IdAware.class);

        if (!violations.isEmpty()) {
            val msg = violations.toString();
            throw new InvalidMessageException(UnitMessage.class, msg);
        }
    }
}
