package fr.istic.sit.codisgroupea.controller;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.message.send.InitializeApplicationMessage;
import fr.istic.sit.codisgroupea.model.message.VehicleMessage;
import fr.istic.sit.codisgroupea.repository.*;
import fr.istic.sit.codisgroupea.service.AuthenticationService;
import lombok.val;
import org.apache.logging.log4j.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static fr.istic.sit.codisgroupea.model.message.send.InitializeApplicationMessage.*;

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

    /** {@link PhotoRepository} instance */
    private PhotoRepository photoRepository;

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
     * @param unitRepository {@link PhotoRepository} instance
     */
    public AuthenticationController(AuthenticationService authenticationService,
                                    SimpMessagingTemplate simpMessagingTemplate,
                                    InterventionRepository interventionRepository,
                                    VehicleTypeRepository vehicleTypeRepository,
                                    SinisterCodeRepository sinisterCodeRepository,
                                    VehicleRepository vehicleRepository,
                                    UnitRepository unitRepository,
                                    PhotoRepository photoRepository) {
        this.authenticationService = authenticationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.interventionRepository = interventionRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.sinisterCodeRepository = sinisterCodeRepository;
        this.vehicleRepository = vehicleRepository;
        this.unitRepository = unitRepository;
        this.photoRepository = photoRepository;
    }


    /**
     * Requested by android client when he connected to the application.
     * @param principal Injected param who contain the user login who send the request
     * @param u         Username in the url. Useless
     */
    @SuppressWarnings("unused")
    @MessageMapping(RoutesConfig.SUBSCRIBED)
    public void getInfoUser(Principal principal,
                            @DestinationVariable("username") final String u) {
        logger.trace(RoutesConfig.SUBSCRIBED
                + " --> data receive : empty string");
        logger.info("msg receive from " + principal.getName());

        val user = authenticationService.getUser(principal.getName());

        if (!user.isPresent()){
            logger.error(principal.getName() + " doesn't exist in the bdd");
        }

        val types = populateList(
                vehicleTypeRepository.findAll(),
                VehicleTypeMessage::new);

        val codes = populateList(
                sinisterCodeRepository.findAll(),
                SinisterCodeMessage::new);

        val vehicles = populateList(
                vehicleRepository.findAll(),
                VehicleMessage::new);

        val demands = populateList(
                unitRepository.getAllRequestedVehicles(),
                DemandMessage::new);

        val interventionsAvailable = populateList(
                interventionRepository.findAllByOpened(true),
                InterventionMessage::new);

        List<InitializeApplicationMessage.PhotoMessage> photoAvailable = new ArrayList<>();
        for (Photo photo : photoRepository.findAll()){
            photoAvailable.add(new InitializeApplicationMessage.PhotoMessage(photo));
        }
        // The check is redundant with the one above.
        @SuppressWarnings({"unchecked", "ConstantConditions"})
        val iniAppli = new InitializeApplicationMessage(user.get(),
                types, codes, vehicles, demands, interventionsAvailable);

        String urlToSend = "/topic/users/" + principal.getName()
                + "/initialize-application";

        Gson gson = new Gson();
        val toJson = gson.toJson(iniAppli, InitializeApplicationMessage.class);

        logger.trace("{} --> data send {}", urlToSend, toJson);
        simpMessagingTemplate.convertAndSend(urlToSend, toJson);
    }

    /**
     * Take a list of E and return a list of M,
     * converting each element of the list using the function f.
     *
     * @param list the input list
     * @param f    the conversion function
     * @param <E>  the type of the input list elements
     * @param <M>  the type of the output list elements
     * @return     a list of M
     */
    private <E, M> List<M> populateList(List<E> list, Function<E, M> f) {
        return list.parallelStream()
                .map(f)
                .collect(Collectors.toList());
    }
}
