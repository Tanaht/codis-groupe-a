package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.SinisterCode;
import fr.istic.sit.codisgroupea.model.message.intervention.CreateInterventionMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.IdMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.InterventionCreatedMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.Position;
import fr.istic.sit.codisgroupea.repository.InterventionRepository;
import fr.istic.sit.codisgroupea.repository.SinisterCodeRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

/**
 * Controller for intervention basic routes
 * /app/interventions/...
 */
@Controller
public class InterventionSocketController {

    /** Template of the web socket */
    private SimpMessagingTemplate simpMessagingTemplate;

    private InterventionRepository interventionRepository;

    private SinisterCodeRepository sinisterCodeRepository;

    /**
     * Constructor of the class {@link InterventionSocketController}.
     *
     * @param simpMessagingTemplate Template of the web socket
     */
    public InterventionSocketController (SimpMessagingTemplate simpMessagingTemplate,
                                         InterventionRepository interventionRepository,
                                         SinisterCodeRepository sinisterCodeRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.interventionRepository = interventionRepository;
        this.sinisterCodeRepository = sinisterCodeRepository;
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
    public String chooseIntervention(@DestinationVariable("id") final String id,
                                     Principal principal,
                                     String dataSentByClient) {


        return "";
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
                sinisterCode
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
    public IdMessage closeIntervention(@DestinationVariable("id") final String id,
                                       Principal principal,
                                       String dataSentByClient) {
        return new IdMessage(Integer.valueOf(id));
    }
}
