package ila.fr.codisintervention.models.model;

import java.sql.Timestamp;

/**
 * Representation of a tactical unit. A unit is a vehicle within an intervention.
 *
 * @see Vehicle
 * @see InterventionModel
 */
public class Unit {

    /** The id of the unit */
    private Integer id;

    /** Instance of {@link InterventionModel} for the intervention of the unit */
    private InterventionModel intervention;

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
    public Unit(InterventionModel intervention,
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
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the intervention.
     *
     * @return the intervention
     */
    public InterventionModel getIntervention() {
        return intervention;
    }

    /**
     * Sets intervention.
     *
     * @param intervention the intervention
     */
    public void setIntervention(InterventionModel intervention) {
        this.intervention = intervention;
    }

    /**
     * Gets vehicle.
     *
     * @return the vehicle
     */
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


    public SymbolSitac getSymbolSitac() {
        return symbolSitac;
    }

    public void setSymbolSitac(SymbolSitac symbolSitac) {
        this.symbolSitac = symbolSitac;
    }
}
