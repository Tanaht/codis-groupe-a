package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.intervention.*;
import fr.istic.sit.codisgroupea.model.message.intervention.Position;
import fr.istic.sit.codisgroupea.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller for intervention basic routes
 */
@Controller
public class InterventionSocketController {

    /** The logger */
    private static final Logger logger = LoggerFactory.getLogger(InterventionSocketController.class);

    private SimpMessagingTemplate simpMessagingTemplate;

    /** {@link InterventionRepository} instance */
    private InterventionRepository interventionRepository;

    /** {@link SinisterCodeRepository} instance */
    private SinisterCodeRepository sinisterCodeRepository;

    /** {@link SymbolSitacRepository} instance */
    private SymbolSitacRepository symbolSitacRepository;

    /** {@link UnitRepository} instance */
    private UnitRepository unitRepository;

    /** {@link PhotoRepository} instance */
    private PhotoRepository photoRepository;

    /** {@link PositionRepository} instance */
    private PositionRepository positionRepository;

    /**
     * Constructor of the class {@link InterventionSocketController}.
     *  @param simpMessagingTemplate
     * @param interventionRepository {@link InterventionRepository} instance
     * @param sinisterCodeRepository {@link SinisterCodeRepository} instance
     * @param symbolSitacRepository {@link SymbolSitacRepository} instance
     * @param unitRepository {@link UnitRepository} instance
     * @param photoRepository {@link PhotoRepository} instance
     * @param positionRepository
     */
    public InterventionSocketController(SimpMessagingTemplate simpMessagingTemplate, InterventionRepository interventionRepository,
                                        SinisterCodeRepository sinisterCodeRepository,
                                        SymbolSitacRepository symbolSitacRepository,
                                        UnitRepository unitRepository,
                                        PhotoRepository photoRepository, PositionRepository positionRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.interventionRepository = interventionRepository;
        this.sinisterCodeRepository = sinisterCodeRepository;
        this.symbolSitacRepository = symbolSitacRepository;
        this.unitRepository = unitRepository;
        this.photoRepository = photoRepository;
        this.positionRepository = positionRepository;
    }

    /**
     * Method which populate a list of symbol
     *
     * @param intervention the intervention
     * @return a list of symbol
     */
    private List<InterventionChosenMessage.Symbol> populateSymbolList(Intervention intervention) {
        List<InterventionChosenMessage.Symbol> symbols = new ArrayList<>();

        for(SymbolSitac symSitac : symbolSitacRepository.findAllByIntervention(intervention)) {
            Symbol actualSymbol = symSitac.getSymbol();
            Payload payload = symSitac.getPayload();

            InterventionChosenMessage.Symbol sym = new InterventionChosenMessage.Symbol(
                    actualSymbol.getId(),
                    actualSymbol.getShape().toString(),
                    actualSymbol.getColor().toString(),
                    new Position(symSitac.getLocation()),
                    new InterventionChosenMessage.Symbol.Payload(payload.getIdentifier(), payload.getDetails())
            );

            symbols.add(sym);
        }

        return symbols;
    }

    /**
     * Method which populate a list of unit
     *
     * @param intervention the intervention
     * @return a list of unit
     */
    private List<InterventionChosenMessage.Unit> populateUnitList(Intervention intervention) {
        List<InterventionChosenMessage.Unit> units = new ArrayList<>();

        for(Unit unit : unitRepository.findAllByIntervention(intervention)) {
            Vehicle vehicle = unit.getVehicle();
            Symbol symbol = unit.getSymbolSitac().getSymbol();

            InterventionChosenMessage.Unit unitObject = new InterventionChosenMessage.Unit(
                    unit.getId(),
                    unit.getRequestDate().getTime(),
                    unit.getAcceptDate().getTime(),
                    unit.isMoving(),
                    new InterventionChosenMessage.Unit.Vehicle(
                            vehicle.getLabel(),
                            vehicle.getType().toString(),
                            vehicle.getStatus().toString()
                    ),
                    new InterventionChosenMessage.Unit.Symbol(
                            symbol.getColor().toString(),
                            symbol.getShape().toString(),
                            new Position(unit.getSymbolSitac().getLocation())
                    )
            );

            units.add(unitObject);
        }

        return units;
    }

    /**
     * Method which populate a list of photo
     *
     * @param intervention the intervention
     * @return a list of photo
     */
    private List<InterventionChosenMessage.Photo> populatePhotoList(Intervention intervention) {
        List<InterventionChosenMessage.Photo> photos = new ArrayList<>();

        for(Photo photo : photoRepository.findAllByIntervention(intervention)) {
            InterventionChosenMessage.Photo photoObject = new InterventionChosenMessage.Photo(
                    photo.getUri(),
                    photo.getDate().getTime(),
                    new Position(photo.getCoordinates())
            );

            photos.add(photoObject);
        }

        return photos;
    }

    /**
     * Choose intervention.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.CHOOSE_INTERVENTION_CLIENT)
    public void chooseIntervention(@DestinationVariable("id") final int id,
                                                        Principal principal,
                                                        String dataSentByClient) {
        logger.trace(RoutesConfig.CHOOSE_INTERVENTION_CLIENT +" --> data receive "+dataSentByClient);
        String username = principal.getName();
        Intervention intervention = interventionRepository.getOne(id);


        Gson gson = new Gson();

        String toJson = gson.toJson(new InterventionChosenMessage(
                id,
                populateSymbolList(intervention),
                populateUnitList(intervention),
                populatePhotoList(intervention)
        ));

        String urlToSend = "/topic/users/"+username+"/intervention-chosen";
        logger.trace(urlToSend+" --> data send "+toJson);
        simpMessagingTemplate.convertAndSend(urlToSend, toJson);
    }

    /**
     * Create intervention.
     *
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.CREATE_INTERVENTION_CLIENT)
    @SendTo({RoutesConfig.CREATE_INTERVENTION_SERVER})
    public InterventionCreatedMessage createIntervention(Principal principal,
                                                         CreateInterventionMessage dataSentByClient) {
        Gson jason = new Gson();

        logger.trace(RoutesConfig.CREATE_INTERVENTION_CLIENT +" --> data receive "+jason.toJson(dataSentByClient));


        SinisterCode sinisterCode = sinisterCodeRepository.findByCode(dataSentByClient.code);

        fr.istic.sit.codisgroupea.model.entity.Position pos = dataSentByClient.location.toPositionEntity();

        fr.istic.sit.codisgroupea.model.entity.Position posPersisted = positionRepository.save(pos);

        Intervention intervention = new Intervention();
        intervention.setDate(new Date().getTime());
        intervention.setPosition(posPersisted);
        intervention.setAddress(dataSentByClient.address);
        intervention.setSinisterCode(sinisterCode);
        intervention.setOpened(true);

        Intervention persisted = interventionRepository.save(intervention);

        InterventionCreatedMessage toReturn = new InterventionCreatedMessage(
                persisted.getId(),
                persisted.getDate(),
                persisted.getSinisterCode().toString(),
                persisted.getAddress(),
                true,
                new Position(persisted.getPosition()));

        logger.trace(RoutesConfig.CREATE_INTERVENTION_SERVER+" --> data send "+jason.toJson(toReturn));

        return toReturn;
    }

    /**
     * Close intervention.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.CLOSE_INTERVENTION_CLIENT)
    @SendTo({RoutesConfig.CLOSE_INTERVENTION_SERVER})
    public IdMessage closeIntervention(@DestinationVariable("id") final int id,
                                       Principal principal,
                                       String dataSentByClient) {
        logger.trace(RoutesConfig.CLOSE_INTERVENTION_CLIENT +" --> data receive "+dataSentByClient);

        Intervention intervention = interventionRepository.getOne(id);
        intervention.setOpened(false);
        interventionRepository.save(intervention);

        IdMessage toSend = new IdMessage(id);
        logger.trace(RoutesConfig.CLOSE_INTERVENTION_SERVER +" --> data send "+toSend);
        return toSend;
    }
}
