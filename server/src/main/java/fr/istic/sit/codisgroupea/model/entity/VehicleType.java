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
    private List<Vehicle> vehicles;

    /**
     * Default constructor.
     */
    public VehicleType() {
    }

    /**
     * Constructor by value.
     *
     * @param name the vehicle-type label
     * @param vehicles the list of vehicle of said type
     */
    public VehicleType(String name, List<Vehicle> vehicles) {
        this.name = name;
        this.vehicles = vehicles;
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

    /**
     * Getter of the vehicle list.
     *
     * @return the vehicle list
     */
    @OneToMany
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Setter of the vehicle list.
     *
     * @param vehicles the vehicle list
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
