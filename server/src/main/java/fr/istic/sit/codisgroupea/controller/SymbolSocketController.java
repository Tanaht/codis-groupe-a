package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.receive.SymbolCreateMessage;
import fr.istic.sit.codisgroupea.model.message.receive.SymbolMessage;
import fr.istic.sit.codisgroupea.model.message.send.SymbolsMessage;
import fr.istic.sit.codisgroupea.model.message.intervention.IdMessage;
import fr.istic.sit.codisgroupea.repository.*;
import org.apache.logging.log4j.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for symbol routes.
 */
@Controller
public class SymbolSocketController {

    /** The logger */
    private static final Logger logger = LogManager.getLogger();

    /** {@link InterventionRepository} instance */
    private InterventionRepository interventionRepository;

    /** {@link SymbolRepository} instance */
    private SymbolRepository symbolRepository;

    /** {@link SymbolSitacRepository} instance */
    private SymbolSitacRepository symbolSitacRepository;

    /** {@link PositionRepository} instance */
    private PositionRepository positionRepository;

    /** {@link PayloadRepository} instance */
    private PayloadRepository payloadRepository;


    /**
     * Constructor of the class {@link SymbolSocketController}
     *
     * @param interventionRepository {@link InterventionRepository}
     * @param symbolRepository {@link SymbolRepository} instance
     * @param symbolSitacRepository {@link SymbolSitacRepository} instance
     * @param positionRepository {@link PositionRepository} instance
     * @param payloadRepository {@link PayloadRepository} instance
     */
    public SymbolSocketController (InterventionRepository interventionRepository,
                                   SymbolRepository symbolRepository,
                                   SymbolSitacRepository symbolSitacRepository,
                                   PositionRepository positionRepository,
                                   PayloadRepository payloadRepository) {
        this.interventionRepository = interventionRepository;
        this.symbolRepository = symbolRepository;
        this.symbolSitacRepository = symbolSitacRepository;
        this.positionRepository = positionRepository;
        this.payloadRepository = payloadRepository;
    }

    /**
     * Method to generate a new {@link SymbolMessage}.
     *
     * @param sitac the symbol sitac with the information
     * @return the {@link SymbolMessage}
     */
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
     * Method to create new symbol when the client ask to
     *
     * @param id               the id
     * @param dataSendByClient the data sent by client
     * @return the message
     */
    @MessageMapping(RoutesConfig.CREATE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.CREATE_SYMBOL_SERVER})
    public String createSymbols(@DestinationVariable("id") final int id, List<SymbolCreateMessage> dataSendByClient) {
        Gson jason = new Gson();
        logger.trace(RoutesConfig.CREATE_SYMBOL_CLIENT +" --> data receive "+jason.toJson(dataSendByClient));

        List<SymbolMessage> listMessage = new ArrayList<>();

        for (SymbolCreateMessage data : dataSendByClient) {
            //Get the optional intervention & symbol
            Optional<Symbol> optSymbol = symbolRepository.findSymbolByColorAndShape(data.getColor(), data.getShape());
            Optional<Intervention> optionalIntervention = interventionRepository.findById(id);

            //Verify if the two optional are present
            if(!optSymbol.isPresent()) {
                logger.error("Le symbol n'existe pas. Couleur : "+data.getColor().toString()+" forme : "+data.getShape().toString());
            }


            if(!optionalIntervention.isPresent()){
                logger.error("L'intervention n'existe pas.");
            }

            SymbolSitac symbolSitac;

            if (data.getPayload() == null){
                Position pos = new Position(data.getLocation().getLat(), data.getLocation().getLng());
                Payload payload = new Payload("","");
                SymbolSitac symbSitac = new SymbolSitac(
                        optionalIntervention.get(),
                        optSymbol.get(),
                        pos,payload
                );

                symbolSitac = symbolSitacRepository.save(symbSitac);
            }else{
                symbolSitac = symbolSitacRepository.save(
                        new SymbolSitac(
                                optionalIntervention.get(),
                                optSymbol.get(),
                                positionRepository.save(new Position(data.getLocation().getLat(), data.getLocation().getLng())),
                                payloadRepository.save(new Payload(data.getPayload().getIdentifier(), data.getPayload().getDetails()))
                        )
                );
            }

            //Create the return message
            SymbolMessage symbolMessage = createSymbolMessage(symbolSitac);

            listMessage.add(symbolMessage);
        }

        SymbolsMessage toSend = new SymbolsMessage(SymbolsMessage.Type.CREATE, listMessage);

        String toJson = jason.toJson(toSend);
        logger.trace(RoutesConfig.CREATE_SYMBOL_SERVER +" --> data send "+toJson);
        return toJson;
    }

    /**
     * Method to delete symbol when the client ask to
     *
     * @param id               the id
     * @param dataSendByClient the data sent by client
     * @return the message
     */
    @MessageMapping(RoutesConfig.DELETE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.DELETE_SYMBOL_SERVER})
    public SymbolsMessage deleteSymbols(@DestinationVariable("id") final int id, List<IdMessage> dataSendByClient) {

        Gson jason = new Gson();

        logger.trace(RoutesConfig.DELETE_SYMBOL_CLIENT +" --> data receive "+jason.toJson(dataSendByClient));

        List<SymbolMessage> listMessage = new ArrayList<>();

        if (!interventionRepository.findById(id).isPresent()) {
            logger.error("L'intervention n'existe pas.");
        }

        for (IdMessage idMessage : dataSendByClient) {
            Optional<SymbolSitac> optSitac = symbolSitacRepository.findById(idMessage.id);

            if (!optSitac.isPresent()) {
                logger.error("Le symbol Sitac n'existe pas.");
            }

            SymbolMessage message = createSymbolMessage(optSitac.get());

            listMessage.add(message);

            positionRepository.delete(optSitac.get().getLocation());
            payloadRepository.delete(optSitac.get().getPayload());
            symbolSitacRepository.delete(optSitac.get());
        }

        SymbolsMessage toSend = new SymbolsMessage(SymbolsMessage.Type.DELETE, listMessage);
        logger.trace(RoutesConfig.DELETE_SYMBOL_SERVER +" --> data send "+jason.toJson(toSend));

        return toSend;
    }

    /**
     * Method to update symbol when the client ask to
     *
     * @param id               the id
     * @param dataSendByClient the data sent by client
     * @return the message
     */
    @MessageMapping(RoutesConfig.UPDATE_SYMBOL_CLIENT)
    @SendTo({RoutesConfig.UPDATE_SYMBOL_SERVER})
    public SymbolsMessage updateSymbols(@DestinationVariable("id") final int id, List<SymbolMessage> dataSendByClient) {
        Gson jason = new Gson();
        logger.trace(RoutesConfig.UPDATE_SYMBOL_CLIENT +" --> data receive "+jason.toJson(dataSendByClient));

        List<SymbolMessage> listMessage = new ArrayList<>();

        for (SymbolMessage data : dataSendByClient) {
            //Get the optional intervention & symbol
            Optional<Symbol> optSymbol = symbolRepository.findSymbolByColorAndShape(data.getColor(), data.getShape());
            Optional<Intervention> optionalIntervention = interventionRepository.findById(id);

            //Verify if the two optional are present
            if(!optSymbol.isPresent()) {
                logger.error("Le symbol n'existe pas.");
            }

            if(!optionalIntervention.isPresent()){
                logger.error("L'intervention n'existe pas.");
            }

            //Create a nex SymbolSitac
            Optional <SymbolSitac> optSitac = symbolSitacRepository.findById(data.getId());

            if(!optSitac.isPresent()){
                logger.error("Le symbol Sitac n'existe pas.");
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

        SymbolsMessage toSend = new SymbolsMessage(SymbolsMessage.Type.UPDATE, listMessage);
        logger.trace(RoutesConfig.UPDATE_SYMBOL_SERVER +" --> data send "+jason.toJson(toSend));
        return toSend;
    }
}
