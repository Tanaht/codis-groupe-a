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
    private int id;
    private Intervention intervention;
    private Vehicle vehicle;
    private boolean moving;
    /** request date of the vehicle for the intervention */
    private Timestamp requestDate;
    /** Date of the CODIS acceptation of the vehicle for the intervention */
    private Timestamp acceptDate;

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
     */
    public Unit(Intervention intervention,
                Vehicle vehicle,
                boolean moving,
                Timestamp requestDate,
                Timestamp acceptDate) {
        this.intervention = intervention;
        this.vehicle = vehicle;
        this.moving = moving;
        this.requestDate = requestDate;
        this.acceptDate = acceptDate;
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
    @NotNull
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
}
