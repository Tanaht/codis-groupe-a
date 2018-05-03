package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.exception.VehicleAlreadyAssignedException;
import fr.istic.sit.codisgroupea.exception.VehicleNotFoundException;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.UnitMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.*;
import fr.istic.sit.codisgroupea.model.message.intervention.Position;
import fr.istic.sit.codisgroupea.model.message.utils.Location;
import fr.istic.sit.codisgroupea.repository.*;
import fr.istic.sit.codisgroupea.service.InterventionFactory;
import fr.istic.sit.codisgroupea.sig.stub.ListSigService;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for intervention basic routes
 */
@Controller
@Transactional
public class InterventionSocketController {

    /**
     * The logger
     */
    private static final Logger logger = LogManager.getLogger();

    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * {@link InterventionRepository} instance
     */
    private InterventionRepository interventionRepository;

    /**
     * {@link SinisterCodeRepository} instance
     */
    private SinisterCodeRepository sinisterCodeRepository;

    /**
     * {@link SymbolSitacRepository} instance
     */
    private SymbolSitacRepository symbolSitacRepository;

    /**
     * {@link UnitRepository} instance
     */
    private UnitRepository unitRepository;

    /**
     * {@link PhotoRepository} instance
     */
    private PhotoRepository photoRepository;

    private ListSigService listSigService;

    private InterventionFactory interventionFactory;

    /**
     * Constructor of the class {@link InterventionSocketController}.
     *
     * @param simpMessagingTemplate  the simp messaging template
     * @param interventionRepository {@link InterventionRepository} instance
     * @param sinisterCodeRepository {@link SinisterCodeRepository} instance
     * @param symbolSitacRepository  {@link SymbolSitacRepository} instance
     * @param unitRepository         {@link UnitRepository} instance
     * @param photoRepository        {@link PhotoRepository} instance
     * @param listSigService         the SIG service
     * @param interventionFactory    the intervention factory
     */
    public InterventionSocketController(
            SimpMessagingTemplate simpMessagingTemplate,
            InterventionRepository interventionRepository,
            SinisterCodeRepository sinisterCodeRepository,
            SymbolSitacRepository symbolSitacRepository,
            UnitRepository unitRepository,
            PhotoRepository photoRepository,
            ListSigService listSigService,
            InterventionFactory interventionFactory) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.interventionRepository = interventionRepository;
        this.sinisterCodeRepository = sinisterCodeRepository;
        this.symbolSitacRepository = symbolSitacRepository;
        this.unitRepository = unitRepository;
        this.photoRepository = photoRepository;
        this.listSigService = listSigService;
        this.interventionFactory = interventionFactory;
    }

    /**
     * Method which populate a list of symbol
     *
     * @param intervention the intervention
     * @return a list of symbol
     */
    private List<InterventionChosenMessage.Symbol> populateSymbolList(Intervention intervention) {
        List<InterventionChosenMessage.Symbol> symbols = new ArrayList<>();

        List<SymbolSitac> listSymbol = intervention.getSymbols();
        List<SymbolSitac> listSymbolBouchon = listSigService.getInterventionSymbols(intervention);

        List<SymbolSitac> union = new ArrayList<>(listSymbol);
        union.addAll(listSymbolBouchon);

        for (SymbolSitac symSitac : union) {
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

        for (Unit unit : unitRepository.findAllByIntervention(intervention)) {
            UnitVehicle unitVehicle = unit.getUnitVehicle();
            Symbol symbol = unit.getSymbolSitac().getSymbol();

            String label = unitVehicle.getAssignedVehicle() == null ?
                    "" :
                    unitVehicle.getAssignedVehicle().getLabel();

            val vehicle = new InterventionChosenMessage.Unit.Vehicle(
                    label,
                    unitVehicle.getType().getName(),
                    unitVehicle.getStatus().toString()
            );

            val unitObject = new InterventionChosenMessage.Unit(
                    unit.getId(),
                    unit.getRequestDate().getTime(),
                    unit.isMoving(),
                    vehicle,
                    new InterventionChosenMessage.Unit.Symbol(
                            symbol.getShape().toString(),
                            symbol.getColor().toString(),
                            new Position(unit.getSymbolSitac().getLocation())
                    )
            );

            if (unit.getAcceptDate() != null) {
                unitObject.setDate_granted(unit.getAcceptDate().getTime());
            }

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

        for (Photo photo : photoRepository.findAllByIntervention(intervention)) {
            val photoObject = new InterventionChosenMessage.Photo(
                    photo.getUri(),
                    photo.getDate().getTime(),
                    new Position(photo.getCoordinates())
            );

            photos.add(photoObject);
        }

        return photos;
    }

    /* Constants used in many methods below */
    private static final String DATA_RECEIVE = " --> data receive ";
    private static final String DATA_SEND = " --> data send ";

    /**
     * Choose intervention.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CHOOSE_INTERVENTION_CLIENT)
    public void chooseIntervention(@DestinationVariable("id") final int id,
                                   Principal principal,
                                   String dataSentByClient) {
        logger.trace(RoutesConfig.CHOOSE_INTERVENTION_CLIENT
                + DATA_RECEIVE + dataSentByClient);

        String username = principal.getName();
        Intervention intervention = interventionRepository.getOneById(id);


        Gson gson = new Gson();

        InterventionChosenMessage interv = new InterventionChosenMessage(
                id,
                populateSymbolList(intervention),
                populateUnitList(intervention),
                populatePhotoList(intervention),
                new Location(intervention.getPosition())
        );

        if (intervention.getPathDrone() != null)
            interv.setPathDrone(new PathDrone(intervention.getPathDrone()));

        String toJson = gson.toJson(interv);

        String urlToSend = "/topic/users/" + username + "/intervention-chosen";
        logger.trace("{} --> data send {}", urlToSend, toJson);
        simpMessagingTemplate.convertAndSend(urlToSend, toJson);
    }

    /**
     * Create intervention.
     *
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CREATE_INTERVENTION_CLIENT)
    public void createIntervention(Principal principal, String dataSentByClient) {
        Gson jason = new Gson();
        logger.trace("{} --> data receive {}",
                RoutesConfig.CREATE_INTERVENTION_CLIENT,
                dataSentByClient);

        logger.trace(RoutesConfig.CREATE_INTERVENTION_CLIENT
                + DATA_RECEIVE + dataSentByClient);

        val dataFromClient = jason.fromJson(dataSentByClient,
                CreateInterventionMessage.class);

        try {
            val intervention = interventionFactory.createIntervention
                    (dataFromClient);

            interventionRepository.save(intervention);
            logger.debug("Position: " + intervention.getPosition());
            InterventionCreatedMessage toReturn = new InterventionCreatedMessage(
                    intervention.getId(),
                    intervention.getDate(),
                    intervention.getSinisterCode().getCode(),
                    intervention.getAddress(),
                    true,
                    new Location(intervention.getPosition()));

            List<UnitMessage> unitMessages = new ArrayList<>();

            for (Unit unit : intervention.getUnits()) {
                unitMessages.add(new UnitMessage(unit));
            }

            toReturn.setUnits(unitMessages);
            String toJson = jason.toJson(toReturn);
            logger.trace(RoutesConfig.CREATE_INTERVENTION_SERVER
                    + " --> data send " + toJson);
            simpMessagingTemplate.convertAndSend(
                    RoutesConfig.CREATE_INTERVENTION_SERVER, toJson);
        } catch (VehicleAlreadyAssignedException | VehicleNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Close intervention.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSentByClient the data sent by client
     */
    @MessageMapping(RoutesConfig.CLOSE_INTERVENTION_CLIENT)
    public void closeIntervention(@DestinationVariable("id") final int id,
                                  Principal principal,
                                  String dataSentByClient) {
        logger.trace(RoutesConfig.CLOSE_INTERVENTION_CLIENT
                + DATA_RECEIVE + dataSentByClient);

        Intervention intervention = interventionRepository.getOne(id);
        intervention.setOpened(false);
        interventionRepository.save(intervention);

        IdMessage toSend = new IdMessage(id);

        Gson jason = new Gson();

        String toJson = jason.toJson(toSend);
        logger.trace(RoutesConfig.CLOSE_INTERVENTION_SERVER
                + DATA_SEND + toJson);

        simpMessagingTemplate.convertAndSend(
                RoutesConfig.CLOSE_INTERVENTION_SERVER, toJson);
    }
}
