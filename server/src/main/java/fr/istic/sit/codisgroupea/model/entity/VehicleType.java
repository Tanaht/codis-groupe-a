package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Encapsulation of a vehicle type.
 */
@Entity
@Data
@NoArgsConstructor
public class VehicleType {

    /** The id of the vehicle type */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /** The name of the vehicle type */
    @NotNull
    private String name;

    /**
     * Constructor by value.
     *
     * @param name the vehicle-type label
     */
    public VehicleType(String name) {
        this.name = name;
    }
}
