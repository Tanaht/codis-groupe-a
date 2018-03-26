package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.User;
import fr.istic.sit.codisgroupea.model.message.InitializeApplicationMessage;
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
     *
     * @param principal
     * @param username
     * @param dataSendByClient
     */
    @MessageMapping(RoutesConfig.SUBSCRIBED)
    public void getInfoUser(Principal principal, @DestinationVariable("username") final String username, String dataSendByClient) {
        logger.info("msg receive from : "+principal.getName()+", msg : " + dataSendByClient);

        Optional<User> user = authenticationService.getUser(principal.getName());

        if (!user.isPresent()){
            logger.error(principal.getName() + " doesn't exist in the bdd");
        }

        List<InitializeApplicationMessage.VehicleTypeMessage> types = new ArrayList<>();
        List<InitializeApplicationMessage.SinisterCodeMessage> codes = new ArrayList<>();
        List<InitializeApplicationMessage.VehicleMessage> vehicles = new ArrayList<>();
        List<InitializeApplicationMessage.DemmandeMessage> demandes = new ArrayList<>();
        List<InitializeApplicationMessage.VehicleColorMapping> vehicleColorMapping = new ArrayList<>();




        InitializeApplicationMessage iniAppli = new InitializeApplicationMessage(user.get(),
                types,codes,vehicles,demandes,vehicleColorMapping);

        Gson gson = new Gson();
        simpMessagingTemplate.convertAndSend("/topic/users/"+principal.getName()+"/initialize-application",
                gson.toJson(iniAppli,InitializeApplicationMessage.class));
    }
}
