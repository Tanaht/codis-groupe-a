package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.Receive.SymbolCreateMessage;
import fr.istic.sit.codisgroupea.model.message.Receive.SymbolMessage;
import fr.istic.sit.codisgroupea.model.message.Send.SymbolsMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.IdMessage;
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
import java.util.List;
import java.util.Optional;

/**
 * Controller for symbol routes.
 */
@Controller
public class SymbolSocketController {

    /** Template of the web socket */
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(SymbolSocketController.class);

    private InterventionRepository interventionRepository;
    private SymbolRepository symbolRepository;
    private SymbolSitacRepository symbolSitacRepository;
    private PositionRepository positionRepository;
    private PayloadRepository payloadRepository;


    /**
     * Constructor of the class {@link SymbolSocketController}
     * @param simpMessagingTemplate Template of the web socket
     */
    public SymbolSocketController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    private SymbolMessage createSymbolMessage (SymbolSitac sitac) {
        //Get the value of the SymbolSitac object
        Symbol symbol = sitac.getSymbol();
        Position location = sitac.getLocation();
        Payload payload = sitac.getPayload();

        //Create the return message
        return new SymbolMessage(
                symbol.getId(),
                symbol.getShape(),
                symbol.getColor(),
                new SymbolMessage.Location(location.getLatitude(), location.getLongitude()),
                new SymbolMessage.Payload(payload.getIdentifier(), payload.getDetails())
        );
    }

    /**
     * Method to create new symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the message
     */
    @MessageMapping(RoutesConfig.CREATE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.CREATE_SYMBOL_SERVER})
    public SymbolsMessage createSymbols(@DestinationVariable("id") final Long id, Principal principal, List<SymbolCreateMessage> dataSendByClient) {

        List<SymbolMessage> listMessage = new ArrayList<SymbolMessage>();

        for (SymbolCreateMessage data : dataSendByClient) {
            //Get the optional intervention & symbol
            Optional<Symbol> optSymbol = symbolRepository.findSymbolByColorAndShape(data.getColor(), data.getShape());
            Optional<Intervention> optionalIntervention = interventionRepository.findById(id);

            //Verify if the two optional are present
            if(!optSymbol.isPresent()) {
                logger.error("Le symbol n'existe pas.");
            }

            if(!optionalIntervention.isPresent()){
                logger.error("Le symbol n'existe pas.");
            }

            //Create a nex SymbolSitac
            SymbolSitac symbolSitac = symbolSitacRepository.save(
                    new SymbolSitac(
                            optionalIntervention.get(),
                            optSymbol.get(),
                            positionRepository.save(new Position(data.getLocation().getLat(), data.getLocation().getLng())),
                            payloadRepository.save(new Payload(data.getPayload().getIdentifier(), data.getPayload().getDetails()))
                    )
            );

            //Create the return message
            SymbolMessage symbolMessage = createSymbolMessage(symbolSitac);

            listMessage.add(symbolMessage);
        }


        return new SymbolsMessage(SymbolsMessage.Type.CREATE, listMessage);
    }

    /**
     * Method to delete symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the message
     */
    @MessageMapping(RoutesConfig.DELETE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.DELETE_SYMBOL_SERVER})
    public SymbolsMessage deleteSymbols(@DestinationVariable("id") final String id, Principal principal, List<IdMessage> dataSendByClient) {

        List<SymbolMessage> listMessage = new ArrayList<SymbolMessage>();

        for (IdMessage idMessage : dataSendByClient) {
            Long idSymbole = new Long(idMessage.id);
            Optional<SymbolSitac> optSitac = symbolSitacRepository.findById(idSymbole);

            if (!optSitac.isPresent()) {
                logger.error("Le symbolSitac n'existe pas.");
            }

            SymbolMessage message = createSymbolMessage(optSitac.get());

            listMessage.add(message);

            positionRepository.delete(optSitac.get().getLocation());
            payloadRepository.delete(optSitac.get().getPayload());
            symbolSitacRepository.delete(optSitac.get());
        }

        return new SymbolsMessage(SymbolsMessage.Type.DELETE, listMessage);
    }

    /**
     * Method to update symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the message
     */
    @MessageMapping(RoutesConfig.UPDATE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.UPDATE_SYMBOL_SERVER})
    public SymbolsMessage updateSymbols(@DestinationVariable("id") final Long id, Principal principal, List<SymbolMessage> dataSendByClient) {

        List<SymbolMessage> listMessage = new ArrayList<SymbolMessage>();

        for (SymbolMessage data : dataSendByClient) {
            //Get the optional intervention & symbol
            Optional<Symbol> optSymbol = symbolRepository.findSymbolByColorAndShape(data.getColor(), data.getShape());
            Optional<Intervention> optionalIntervention = interventionRepository.findById(id);

            //Verify if the two optional are present
            if(!optSymbol.isPresent()) {
                logger.error("Le symbol n'existe pas.");
            }

            if(!optionalIntervention.isPresent()){
                logger.error("Le symbol n'existe pas.");
            }

            //Create a nex SymbolSitac
            Long idSitac = new Long(data.getId());
            Optional <SymbolSitac> optSitac = symbolSitacRepository.findById(idSitac);

            if(!optSitac.isPresent()){
                logger.error("Le symbolSitac n'existe pas.");
            }

            SymbolSitac symbolSitac = optSitac.get();

            symbolSitac.setId(data.getId());
            symbolSitac.setSymbol(optSymbol.get());
            symbolSitac.getLocation().setLatitude(data.getLocation().getLat());
            symbolSitac.getLocation().setLongitude(data.getLocation().getLng());
            symbolSitac.getPayload().setIdentifier(data.getPayload().getIdentifier());
            symbolSitac.getPayload().setDetails(data.getPayload().getDetails());

            SymbolSitac newSymbolSitac = symbolSitacRepository.save(symbolSitac);

            //Create the return message
            SymbolMessage symbolMessage = createSymbolMessage(newSymbolSitac);

            listMessage.add(symbolMessage);
        }


        return new SymbolsMessage(SymbolsMessage.Type.UPDATE, listMessage);
    }
}
