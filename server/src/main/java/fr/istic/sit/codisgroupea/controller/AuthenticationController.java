package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.send.InitializeApplicationMessage;
import fr.istic.sit.codisgroupea.model.message.VehicleMessage;
import fr.istic.sit.codisgroupea.repository.*;
import fr.istic.sit.codisgroupea.service.AuthenticationService;
import org.apache.logging.log4j.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthenticationController {

    /** The logger */
    private static final Logger logger = LogManager.getLogger();

    /** {@link AuthenticationService} instance */
    private AuthenticationService authenticationService;

    /** Template of the web socket  */
    private SimpMessagingTemplate simpMessagingTemplate;

    /** {@link InterventionRepository} instance */
    private InterventionRepository interventionRepository;

    /** {@link VehicleTypeRepository} instance */
    private VehicleTypeRepository vehicleTypeRepository;

    /** {@link SinisterCodeRepository} instance */
    private SinisterCodeRepository sinisterCodeRepository;

    /** {@link VehicleRepository} instance */
    private VehicleRepository vehicleRepository;

    /** {@link UnitRepository} instance */
    private UnitRepository unitRepository;


    /**
     * Constructor of the class {@link AuthenticationController}
     *
     * @param authenticationService {@link AuthenticationService} instance
     * @param simpMessagingTemplate Template of the web socket
     * @param interventionRepository {@link InterventionRepository} instance
     * @param vehicleTypeRepository {@link VehicleTypeRepository} instance
     * @param sinisterCodeRepository {@link SinisterCodeRepository} instance
     * @param vehicleRepository {@link VehicleRepository} instance
     * @param unitRepository {@link UnitRepository} instance
     */
    public AuthenticationController(AuthenticationService authenticationService,
                                    SimpMessagingTemplate simpMessagingTemplate,
                                    InterventionRepository interventionRepository,
                                    VehicleTypeRepository vehicleTypeRepository,
                                    SinisterCodeRepository sinisterCodeRepository,
                                    VehicleRepository vehicleRepository,
                                    UnitRepository unitRepository) {
        this.authenticationService = authenticationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.interventionRepository = interventionRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.sinisterCodeRepository = sinisterCodeRepository;
        this.vehicleRepository = vehicleRepository;
        this.unitRepository = unitRepository;
    }


    /**
     * Requested by android client when he connected to the application.
     * @param principal Injected param who contain the user login who send the request
     * @param username Username in the url. Useless
     */
    @MessageMapping(RoutesConfig.SUBSCRIBED)
    public void getInfoUser(Principal principal, @DestinationVariable("username") final String username) {
        logger.trace(RoutesConfig.SUBSCRIBED +" --> data receive : empty string");

        logger.info("msg receive from "+principal.getName());

        Optional<User> user = authenticationService.getUser(principal.getName());

        if (!user.isPresent()){
            logger.error(principal.getName() + " doesn't exist in the bdd");
        }

        List<InitializeApplicationMessage.VehicleTypeMessage> types = new ArrayList<>();
        for (VehicleType vehicleType : vehicleTypeRepository.findAll()){
            types.add(new InitializeApplicationMessage.VehicleTypeMessage(vehicleType));
        }

        List<InitializeApplicationMessage.SinisterCodeMessage> codes = new ArrayList<>();
        for (SinisterCode sinisterCode : sinisterCodeRepository.findAll()){
            codes.add(new InitializeApplicationMessage.SinisterCodeMessage(sinisterCode));
        }

        List<VehicleMessage> vehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicleRepository.findAll()){
            vehicles.add(new VehicleMessage(vehicle));
        }

        List<InitializeApplicationMessage.DemandMessage> demandes = new ArrayList<>();
        for (Unit unit : unitRepository.getAllRequestedVehicles()){
            demandes.add(new InitializeApplicationMessage.DemandMessage(unit));
        }

        List<InitializeApplicationMessage.InterventionMessage> interventionsAvailable = new ArrayList<>();
        for (Intervention intervention : interventionRepository.findAllByOpened(true)){
            interventionsAvailable.add(new InitializeApplicationMessage.InterventionMessage(intervention));
        }


        InitializeApplicationMessage iniAppli = new InitializeApplicationMessage(user.get(),
                types,codes,vehicles,demandes, interventionsAvailable);

        String urlToSend = "/topic/users/"+principal.getName()+"/initialize-application";

        Gson gson = new Gson();
        String toJson = gson.toJson(iniAppli,InitializeApplicationMessage.class);
        logger.trace(urlToSend+" --> data send "+toJson);
        simpMessagingTemplate.convertAndSend(urlToSend, toJson);
    }
}
