package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Representation of a tactical unit. A unit is a vehicle within an intervention.
 *
 * @see Vehicle
 * @see Intervention
 */
@Entity
@Data
public class Unit {
    /** The id of the unit */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * Association of the vehicle is done here
     * From this class we can gain the instance of the vehicle
     * @see Vehicle
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "unit")
    @NotNull
    private UnitVehicle unitVehicle;

    /** Instance of {@link Intervention} for the intervention of the unit */
    @ManyToOne
    @NotNull
    private Intervention intervention;

    /** Boolean which tells if the unit is moving or not */
    @NotNull
    private boolean moving;

    /** request date of the vehicle for the intervention */
    @NotNull
    private Timestamp requestDate;

    /** Date of the CODIS acceptation of the vehicle for the intervention */
    private Timestamp acceptDate;

    /** Date of the first commitment of the vehicle for the intervention */
    private Timestamp commitedDate;

    /** Date of the release of the vehicle from the intervention */
    private Timestamp releasedDate;

    /** Instance of {@link SymbolSitac} for the symbol sitac of the unit */
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private SymbolSitac symbolSitac;


    /**
     * Constructor that instanciate a Unit in an Intervention context,
     * it Will initialize the Request Date and the related UnitVehicle instance
     * @param intervention the intervention
     */
    public Unit(Intervention intervention) {
        this.moving = false;
        this.intervention = intervention;
        this.unitVehicle = new UnitVehicle(this);
        this.requestDate = new Timestamp(new Date().getTime());
    }

    /**
     * No Arg Constructor
     */
    public Unit() {
        this.requestDate = new Timestamp(new Date().getTime());
    }

    /**
     * Constructor by value.
     *
     * @param intervention the intervention
     * @param vehicle      the vehicle
     * @param moving       is the unit on the way to its target
     * @param requestDate  the request date
     * @param acceptDate   the acceptation date
     * @param symbolSitac  the symbol
     */
    public Unit(Intervention intervention,
                Vehicle vehicle,
                boolean moving,
                Timestamp requestDate,
                Timestamp acceptDate,
                SymbolSitac symbolSitac) {
        this.intervention = intervention;
        this.unitVehicle.setAssignedVehicle(vehicle);
        this.moving = moving;
        this.requestDate = requestDate;
        this.acceptDate = acceptDate;
        this.symbolSitac = symbolSitac;
    }

    /**
     * Gets vehicle.
     *
     * @return the vehicle
     */
    @Transient
    public Vehicle getVehicle() {
        return this.unitVehicle.getAssignedVehicle();
    }

    /**
     * Sets vehicle.
     *
     * @param vehicle the vehicle
     */
    @Transient
    public void setVehicle(Vehicle vehicle) {
        this.unitVehicle.setAssignedVehicle(vehicle);
    }
}
