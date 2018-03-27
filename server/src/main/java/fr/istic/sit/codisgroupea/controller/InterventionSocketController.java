package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.intervention.*;
import fr.istic.sit.codisgroupea.model.message.intervention.Position;
import fr.istic.sit.codisgroupea.repository.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

    /**
     * Constructor of the class {@link InterventionSocketController}.
     *
     * @param interventionRepository {@link InterventionRepository} instance
     * @param sinisterCodeRepository {@link SinisterCodeRepository} instance
     * @param symbolSitacRepository {@link SymbolSitacRepository} instance
     * @param unitRepository {@link UnitRepository} instance
     * @param photoRepository {@link PhotoRepository} instance
     */
    public InterventionSocketController (InterventionRepository interventionRepository,
                                         SinisterCodeRepository sinisterCodeRepository,
                                         SymbolSitacRepository symbolSitacRepository,
                                         UnitRepository unitRepository,
                                         PhotoRepository photoRepository) {
        this.interventionRepository = interventionRepository;
        this.sinisterCodeRepository = sinisterCodeRepository;
        this.symbolSitacRepository = symbolSitacRepository;
        this.unitRepository = unitRepository;
        this.photoRepository = photoRepository;
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
    @SendTo({RoutesConfig.CHOOSE_INTERVENTION_SERVER})
    public InterventionChosenMessage chooseIntervention(@DestinationVariable("id") final int id,
                                                        Principal principal,
                                                        String dataSentByClient) {
        Intervention intervention = interventionRepository.getOne((long) id);

        return new InterventionChosenMessage(
                id,
                populateSymbolList(intervention),
                populateUnitList(intervention),
                populatePhotoList(intervention)
        );
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
        SinisterCode sinisterCode = sinisterCodeRepository.findByCode(dataSentByClient.code);

        Intervention intervention = new Intervention(
                new Date().getTime(),
                dataSentByClient.location.toPositionEntity(),
                dataSentByClient.address,
                sinisterCode,
                true
        );

        Intervention persisted = interventionRepository.save(intervention);

        return new InterventionCreatedMessage(
                persisted.getId(),
                persisted.getDate(),
                persisted.getSinisterCode().toString(),
                persisted.getAddress(),
                true,
                new Position(persisted.getPosition())
        );
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
        Intervention intervention = interventionRepository.getOne((long) id);
        intervention.setOpened(false);
        interventionRepository.save(intervention);
        return new IdMessage(id);
    }
}
