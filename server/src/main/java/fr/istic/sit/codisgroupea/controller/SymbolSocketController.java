package fr.istic.sit.codisgroupea.controller;

import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.Payload;
import fr.istic.sit.codisgroupea.model.entity.Position;
import fr.istic.sit.codisgroupea.model.entity.Symbol;
import fr.istic.sit.codisgroupea.model.entity.SymbolSitac;
import fr.istic.sit.codisgroupea.model.message.Receive.SymbolCreateMessage;
import fr.istic.sit.codisgroupea.model.message.Receive.SymbolMessage;
import fr.istic.sit.codisgroupea.model.message.Send.SymbolsMessage;
import fr.istic.sit.codisgroupea.repository.PayloadRepository;
import fr.istic.sit.codisgroupea.repository.PositionRepository;
import fr.istic.sit.codisgroupea.repository.SymbolRepository;
import fr.istic.sit.codisgroupea.repository.SymbolSitacRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;

/**
 * Controller for symbol routes.
 */
@Controller
public class SymbolSocketController {

    /** Template of the web socket */
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(SymbolSocketController.class);

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

    /**
     * Method to create new symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.CREATE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.CREATE_SYMBOL_SERVER})
    public SymbolsMessage createSymbols(@DestinationVariable("id") final String id, Principal principal, SymbolCreateMessage dataSendByClient) {

        Optional<Symbol> optSymbol = symbolRepository.findSymbolByColorAndShape(dataSendByClient.getColor(), dataSendByClient.getShape());

        if(!optSymbol.isPresent()) {
            logger.error("Le symbol n'existe pas.");
        }

        SymbolSitac symbolSitac = symbolSitacRepository.save(
                new SymbolSitac(
                        optSymbol.get(),
                        positionRepository.save(new Position(dataSendByClient.getLocation().getLat(), dataSendByClient.getLocation().getLng())),
                        payloadRepository.save(new Payload(dataSendByClient.getPayload().getIdentifier(), dataSendByClient.getPayload().getDetails()))
                )
        );

        Symbol symbol = symbolSitac.getSymbol();
        Position location = symbolSitac.getLocation();
        Payload payload = symbolSitac.getPayload();

        SymbolMessage symbolMessage = new SymbolMessage(
                symbol.getId(),
                symbol.getShape(),
                symbol.getColor(),
                new SymbolMessage.Location(location.getLatitude(), location.getLongitude()),
                new SymbolMessage.Payload(payload.getIdentifier(), payload.getDetails())
        );

        return new SymbolsMessage(SymbolsMessage.Type.CREATE, symbolMessage);
    }

    /**
     * Method to delete symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.DELETE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.DELETE_SYMBOL_SERVER})
    public String deleteSymbols(@DestinationVariable("id") final String id, @DestinationVariable("id_symb") final String id_symb, Principal principal, String dataSendByClient) {
        return "";
    }

    /**
     * Method to update symbol when the client ask to.
     *
     * @param id               the id
     * @param principal        the principal
     * @param dataSendByClient the data sent by client
     * @return the string
     */
    @MessageMapping(RoutesConfig.UPDATE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.UPDATE_SYMBOL_SERVER})
    public String updateSymbols(@DestinationVariable("id") final String id, @DestinationVariable("id_symb") final String id_symb, Principal principal, String dataSendByClient) {
        return "";
    }
}
