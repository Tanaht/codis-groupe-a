package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class is the vehicle entity. It represents an actual vehicle, not a tactical unit.
 *
 * @see Unit
 */
@Entity
@Data
@NoArgsConstructor
public class Vehicle {
    /** The id of the vehicle */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /** The label of the vehicle */
    @NotNull
    private String label;

    /**
     * Attribute that associate a vehicle with a Unit
     * In this class we can gain the current status of the vehicle in the context of the unit
     */
    @OneToOne(mappedBy = "assignedVehicle")
    private UnitVehicle unitVehicle;

    /** Instance of the {@link VehicleType} for the type of the vehicle */
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private VehicleType type;

    /**
     * Constructor by value.
     *
     * @param label the vehicle unique label
     * @param type the vehicle type
     */
    public Vehicle(String label, VehicleType type) {
        this.label = label;
        this.type = type;
    }

    /**
     * Getter of the vehicle status.
     * A vehicle not linked to a Unit has always the state AVAILABLE
     * @return the vehicle status
     */
    @Transient
    public VehicleStatus getStatus() {
        return unitVehicle == null ? VehicleStatus.AVAILABLE : unitVehicle.getStatus();
    }
}
