package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Representation of a tactical unit. A unit is a vehicle within an intervention.
 *
 * @see Vehicle
 * @see Intervention
 */
@Entity
public class Unit {

    /** The id of the unit */
    private int id;

    /** Instance of {@link Intervention} for the intervention of the unit */
    private Intervention intervention;

    /** Instance of {@link Vehicle} for the vehicle of the unit */
    private Vehicle vehicle;

    /** Boolean which tells if the unit is moving or not */
    private boolean moving;

    /** request date of the vehicle for the intervention */
    private Timestamp requestDate;

    /** Date of the CODIS acceptation of the vehicle for the intervention */
    private Timestamp acceptDate;

    /** Instance of {@link SymbolSitac} for the symbol sitac of the unit */
    private SymbolSitac symbolSitac;

    /**
     * Default constructor.
     */
    public Unit() {

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
        this.vehicle = vehicle;
        this.moving = moving;
        this.requestDate = requestDate;
        this.acceptDate = acceptDate;
        this.symbolSitac = symbolSitac;
    }

    /**
     * Gets ID.
     *
     * @return the ID
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the intervention.
     *
     * @return the intervention
     */
    @ManyToOne
    @NotNull
    public Intervention getIntervention() {
        return intervention;
    }

    /**
     * Sets intervention.
     *
     * @param intervention the intervention
     */
    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }

    /**
     * Gets vehicle.
     *
     * @return the vehicle
     */
    @OneToOne
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Sets vehicle.
     *
     * @param vehicle the vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Is moving boolean.
     *
     * @return the boolean
     */
    @NotNull
    public boolean isMoving() {
        return moving;
    }

    /**
     * Sets moving.
     *
     * @param moving the moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Gets request date.
     *
     * @return the request date
     */
    @NotNull
    public Timestamp getRequestDate() {
        return requestDate;
    }

    /**
     * Sets request date.
     *
     * @param requestDate the request date
     */
    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets accept date.
     *
     * @return the accept date
     */
    @NotNull
    public Timestamp getAcceptDate() {
        return acceptDate;
    }

    /**
     * Sets accept date.
     *
     * @param acceptDate the accept date
     */
    public void setAcceptDate(Timestamp acceptDate) {
        this.acceptDate = acceptDate;
    }


    @OneToOne
    @NotNull
    public SymbolSitac getSymbolSitac() {
        return symbolSitac;
    }

    public void setSymbolSitac(SymbolSitac symbolSitac) {
        this.symbolSitac = symbolSitac;
    }
}
