package ila.fr.codisintervention.models.model;

/**
 * This class is the vehicle entity. It represents an actual vehicle, not a tactical unit.
 *
 */
public class Vehicle {

    /** The id of the vehicle */
    private Integer id;

    /** The label of the vehicle */
    private String label;

    /** Instance of the {@link VehicleType} for the type of the vehicle */
    private VehicleType type;

    /** Instance of {@link VehicleStatus} for the status of the vehicle */
    private VehicleStatus status;

    /**
     * Default constructor.
     */
    public Vehicle() {
    }

    /**
     * Constructor by value.
     *
     * @param label the vehicle unique label
     * @param type the vehicle type
     * @param status the vehicle status
     */
    public Vehicle(String label, VehicleType type, VehicleStatus status) {
        this.label = label;
        this.type = type;
        this.status = status;
    }

    /**
     * Getter of ID.
     *
     * @return the ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of ID.
     *
     * @param id the ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the unique label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter of the vehicle type.
     *
     * @return the vehicle type
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Setter of the vehicle type.
     *
     * @param type the vehicle type
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /**
     * Getter of the vehicle status.
     *
     * @return the vehicle status
     */
    public VehicleStatus getStatus() {
        return status;
    }

    /**
     * Setter of the status.
     *
     * @param status the status
     */
    public void setStatus(VehicleStatus status) {
        this.status = status;
    }
}
