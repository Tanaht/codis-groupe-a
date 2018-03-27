package fr.istic.sit.codisgroupea.config;


/**
 *
 */
public class RoutesConfig {

    //AuthenticationController
    public final static String SUBSCRIBED = "/users/{username}/subscribed";

    //InterventionSocketController
    public final static String CHOOSE_INTERVENTION_CLIENT = "/app/interventions/{id}/choose";
    public final static String CHOOSE_INTERVENTION_SERVER = "/topic/users/{username}/intervention-chosen";

    public final static String CREATE_INTERVENTION_CLIENT = "/app/interventions/create";
    public final static String CREATE_INTERVENTION_SERVER = "/topic/interventions/created";

    public final static String CLOSE_INTERVENTION_CLIENT = "/app/interventions/{id}/close";
    public final static String CLOSE_INTERVENTION_SERVER = "/topic/interventions/closed";

    //DemandeSocketController
    public final static String CREATE_UNIT_CLIENT = "/app/interventions/{id}/units/create";
    public final static String CREATE_UNIT_SERVER_CODIS = "/topic/demandes/created";
    public final static String CREATE_UNIT_SERVER_CLIENT = "/topic/interventions/{id}/units/event";

    public final static String UPDATE_UNIT_CLIENT = "/app/interventions/{id}/units/update";
    public final static String UPDATE_UNIT_SERVER = "/topic/interventions/{id}/units/event";

    public final static String CONFIRM_DEMAND_CLIENT = "/app/demandes/{idUnit}/accept";
    public final static String CONFIRM_DEMAND_SERVER_CODIS = "/topic/demandes/{id}/accepted";
    public final static String CONFIRM_DEMAND_SERVER_CLIENT = "/topic/interventions/{id}/units/{id}/accepted";

    public final static String DENY_DEMAND_CLIENT = "/app/demandes/{idUnit}/deny";
    public final static String DENY_DEMAND_SERVER_CODIS = "/topic/demandes/{id}/denied";
    public final static String DENY_DEMAND_SERVER_CLIENT = "/topic/interventions/{id}/units/{id}/denied";

    //SymbolSocketController
    public final static String CREATE_SYMBOL_CLIENT = "/app/interventions/{id}/symbols/create";
    public final static String CREATE_SYMBOL_SERVER = "/topic/interventions/{id}/symbols/event";

    public final static String DELETE_SYMBOL_CLIENT = "/app/interventions/{id}/symbols/delete";
    public final static String DELETE_SYMBOL_SERVER = "/topic/interventions/{id}/symbols/event";

    public final static String UPDATE_SYMBOL_CLIENT = "/app/interventions/{id}/symbols/update";
    public final static String UPDATE_SYMBOL_SERVER = "/topic/interventions/{id}/symbols/event";
}
