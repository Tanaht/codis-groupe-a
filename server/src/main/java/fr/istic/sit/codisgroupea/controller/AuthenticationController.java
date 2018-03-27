package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.model.message.Send.InitializeApplicationMessage;
import fr.istic.sit.codisgroupea.model.message.VehicleMessage;
import fr.istic.sit.codisgroupea.repository.SinisterCodeRepository;
import fr.istic.sit.codisgroupea.repository.UnitRepository;
import fr.istic.sit.codisgroupea.repository.VehicleRepository;
import fr.istic.sit.codisgroupea.repository.VehicleTypeRepository;
import fr.istic.sit.codisgroupea.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationService authenticationService;
    private SimpMessagingTemplate simpMessagingTemplate;

    private VehicleTypeRepository vehicleTypeRepository;
    private SinisterCodeRepository sinisterCodeRepository;
    private VehicleRepository vehicleRepository;
    private UnitRepository unitRepository;


    public AuthenticationController(AuthenticationService authenticationService, SimpMessagingTemplate simpMessagingTemplate, VehicleTypeRepository vehicleTypeRepository, SinisterCodeRepository sinisterCodeRepository, VehicleRepository vehicleRepository, UnitRepository unitRepository) {
        this.authenticationService = authenticationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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
        logger.info("msg receive from : "+principal.getName());

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

        InitializeApplicationMessage iniAppli = new InitializeApplicationMessage(user.get(),
                types,codes,vehicles,demandes);

        Gson gson = new Gson();
        simpMessagingTemplate.convertAndSend("/topic/users/"+principal.getName()+"/initialize-application",
                gson.toJson(iniAppli,InitializeApplicationMessage.class));
    }
}
