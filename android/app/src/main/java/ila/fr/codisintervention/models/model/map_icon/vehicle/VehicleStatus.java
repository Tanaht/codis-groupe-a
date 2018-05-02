package ila.fr.codisintervention.models.model.map_icon.vehicle;

/**
 * Status of a vehicle.
 */
public enum VehicleStatus {
    /** Requested for an intervention */
    REQUESTED("Demandé"),
    /** Already used in an intervention */
    USED("Utilisé"),
    /** Available for an intervention */
    AVAILABLE("Disponible"),
    /** In intervention, waiting for orders */
    CRM("Au CRM");

    /**
     * French translation of status
     */
    private String translation;


    VehicleStatus(String translation) {
        this.translation = translation;
    }

    public String getTranslation() { return  translation; }

    public static VehicleStatus getStatusEnumFromString(String str){
        if (REQUESTED.name().equals(str)){
            return REQUESTED;
        }else if (USED.name().equals(str)){
            return USED;
        }else if (AVAILABLE.name().equals(str)){
            return AVAILABLE;
        }else if (CRM.name().equals(str)){
            return CRM;
        }
        throw new IllegalArgumentException("Bad string :"+str);
    }


}
