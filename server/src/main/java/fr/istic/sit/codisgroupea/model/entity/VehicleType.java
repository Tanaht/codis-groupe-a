package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Encapsulation of a vehicle type.
 */
@Entity
public class VehicleType {
    private int id;
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
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Setter of ID.
     *
     * @param id the ID
     */
    public void setId(int id) {
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
