package ila.fr.codisintervention.models.model.map_icon;

/**
 * This enum list all  shapes used in this project
 * Created by tanaky on 30/03/18.
 */

public enum Shape {
    /**
     * Area shape.
     */
    AREA,

    /**
     * Triangle down shape.
     */
    DANGERDOWN,

    /**
     * Triangle up shape.
     */
    DANGERUP,

    /**
     * Not effective vehicle shape.
     */
    NOTEFFECTIVEVEHICLE,

    /**
     * Sinister shape.
     */
    SINISTER,

    /**
     * Fire vehicle shape.
     */
    FIREVEHICLE,

    /**
     * Not effective fire vehicle shape.
     */
    NOTEFFECTIVEFIREVEHICLE,

    /**
     * Water resource shape.
     */
    WATERRESOURCE,

    /**
     * Vehicle shape.
     */
    VEHICLE,

    /**
     * Drone point for the path
     */
    DRONE;


    /**
     * add a type for each shape
     * Not the best way
     */
    static public String SYMBOL = "SYMBOL";
    static public String UNIT = "UNIT";
    static public String DRONE_POINT = "DRONE_POINT";


    static public String findAssociatedObject(Shape myShape){
        String ret = SYMBOL;

        switch (myShape) {
            case FIREVEHICLE:
                ret = UNIT;
                break;
            case VEHICLE:
                ret = UNIT;
                break;
            case NOTEFFECTIVEFIREVEHICLE:
                ret = UNIT;
                break;
            case NOTEFFECTIVEVEHICLE:
                ret = UNIT;
                break;
            case DRONE:
                ret = DRONE_POINT;
                break;

        }
        return ret;
    }
}
