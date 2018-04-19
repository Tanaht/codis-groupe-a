package ila.fr.codisintervention.services.constants;

/**
 * Created by gbrossault on 28/03/18.
 *
 * List of Intents names used to define an update of the model
 *
 */

public class ModelConstants {

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when the model is fully initialized
     */
    public static final String INITIALIZE_APPLICATION = "initialize-application";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when a new intervention is added to the model by remote server
     */
    public static final String ADD_INTERVENTION = "add-intervention";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when an intervention is deleted to the model by remote server
     */
    public static final String ACTION_DELETE_INTERVENTION = "delete-intervention";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when one or more units are updated in an intervention
     */
    public static final String UPDATE_INTERVENTION_UPDATE_UNIT = "update-intervention-update-units";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when one or more units are updated in an intervention
     */
    public static final String UPDATE_INTERVENTION_CREATE_UNIT = "update-intervention-create-units";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when one or more units are deleted in an intervention
     */
    public static final String UPDATE_INTERVENTION_DELETE_UNIT = "update-intervention-delete-units";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService}  when one or more symbols are updated in an intervention
     */
    public static final String UPDATE_INTERVENTION_UPDATE_SYMBOL = "update-intervention-update-symbols";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when one or more symbols are deleted in an intervention
     */
    public static final String UPDATE_INTERVENTION_DELETE_SYMBOL = "update-intervention-delete-symbols";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when one or more symbols are created in an intervention
     */
    public static final String UPDATE_INTERVENTION_CREATE_SYMBOL = "update-intervention-create-symbol";


    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when a vehicle request is created
     */
    public static final String ADD_VEHICLE_REQUEST = "add-vehicle-request";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when a vehicle request is accepted
     */
    public static final String VALIDATE_VEHICLE_REQUEST = "validate-vehicle-request";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when a vehicle request is rejected
     */
    public static final String REJECT_VEHICLE_REQUEST = "reject-vehicle-request";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when a photo is added in an intervention
     */
    public static final String ADD_PHOTO = "add-photo";

    /**
     * Intent Name being broadcoasted by {@link ila.fr.codisintervention.services.model.ModelService} when the drone position is updated
     */
    public static final String UPDATE_DRONE_POSITION = "update-drone-position";
}
