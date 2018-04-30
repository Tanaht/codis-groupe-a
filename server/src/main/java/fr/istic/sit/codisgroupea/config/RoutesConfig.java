package fr.istic.sit.codisgroupea.config;


/**
 *
 */
public class RoutesConfig {

    //AuthenticationController
    public static final String SUBSCRIBED = "/users/{username}/subscribed";

    //InterventionSocketController
    public static final String CHOOSE_INTERVENTION_CLIENT = "/app/interventions/{id}/choose";
    //public static final String CHOOSE_INTERVENTION_SERVER = "/topic/users/{username}/intervention-chosen";

    public static final String CREATE_INTERVENTION_CLIENT = "/app/interventions/create";
    public static final String CREATE_INTERVENTION_SERVER = "/topic/interventions/created";

    public static final String CLOSE_INTERVENTION_CLIENT = "/app/interventions/{id}/close";
    public static final String CLOSE_INTERVENTION_SERVER = "/topic/interventions/closed";

    //DemandeSocketController
    public static final String CREATE_UNIT_CLIENT = "/app/interventions/{id}/units/create";
    public static final String CREATE_UNIT_SERVER_CODIS = "/topic/demandes/created";
    public static final String CREATE_UNIT_SERVER_CLIENT = "/topic/interventions/{id}/units/event";

    public static final String UPDATE_UNIT_CLIENT = "/app/interventions/{id}/units/update";
    public static final String UPDATE_UNIT_SERVER = "/topic/interventions/{id}/units/event";

    public static final String CONFIRM_DEMAND_CLIENT = "/app/demandes/{idUnit}/accept";
    public static final String CONFIRM_DEMAND_SERVER_CODIS = "/topic/demandes/{id}/accepted";
    public static final String CONFIRM_DEMAND_SERVER_CLIENT = "/topic/interventions/{id}/units/{idUnit}/accepted";

    public static final String DENY_DEMAND_CLIENT = "/app/demandes/{idUnit}/deny";
    public static final String DENY_DEMAND_SERVER_CODIS = "/topic/demandes/{id}/denied";
    public static final String DENY_DEMAND_SERVER_CLIENT = "/topic/interventions/{id}/units/{idUnit}/denied";

    //SymbolSocketController
    public static final String CREATE_SYMBOL_CLIENT = "/app/interventions/{id}/symbols/create";
    public static final String CREATE_SYMBOL_SERVER = "/topic/interventions/{id}/symbols/event";

    public static final String DELETE_SYMBOL_CLIENT = "/app/interventions/{id}/symbols/delete";
    public static final String DELETE_SYMBOL_SERVER = "/topic/interventions/{id}/symbols/event";

    public static final String UPDATE_SYMBOL_CLIENT = "/app/interventions/{id}/symbols/update";
    public static final String UPDATE_SYMBOL_SERVER = "/topic/interventions/{id}/symbols/event";
    public final static String SEND_DRONE_POSITION_PART1 = "/topic/interventions/";
    public final static String SEND_DRONE_POSITION_PART2 = "/drone/ping";
    public final static String RECEIVE_DRONE_MISSION = "/app/interventions/{id}/drone/path";
    public final static String RECEIVE_DRONE_MISSION_PART1 = "/topic/interventions/";
    public final static String RECEIVE_DRONE_MISSION_PART2 = "/drone/path";

    public static final String SEND_DRONE_PHOTO_PART1 = "/topic/interventions/";
    public static final String SEND_DRONE_PHOTO_PART2 = "/photo";
}
