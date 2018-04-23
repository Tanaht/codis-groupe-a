package ila.fr.codisintervention.models.model;

/**
 * Encapsulation of a vehicle type.
 */
public class VehicleType {

    /** The id of the vehicle type */
    private Integer id;

    /** The name of the vehicle type */
    private String name;

    /**
     * Default constructor.
     */
    public VehicleType() {
    }

    /**
     * Constructor by value.
     *
     * @param name the vehicle-type label
     */
    public VehicleType(String name) {
        this.name = name;
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
     * Getter of the vehicle-type label.
     *
     * @return the vehicle-type label
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the vehicle-type label.
     *
     * @param name the vehicle-type label
     */
    public void setName(String name) {
        this.name = name;
    }

}
