package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Encapsulation of a vehicle type.
 */
@Entity
public class VehicleType {

    /** The id of the vehicle type */
    private Integer id;

    /** The name of the vehicle type */
    private String name;

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
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @NotNull
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
