package fr.istic.sit.codisgroupea.model.entity;

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
public class Unit {

    /** The id of the unit */
    private Integer id;

    /**
     * Association of the vehicle is done here
     * From this class we can gain the instance of the vehicle
     * @see Vehicle
     */
    private UnitVehicle unitVehicle;

    /** Instance of {@link Intervention} for the intervention of the unit */
    private Intervention intervention;

    /** Boolean which tells if the unit is moving or not */
    private boolean moving;

    /** request date of the vehicle for the intervention */
    private Timestamp requestDate;

    /** Date of the CODIS acceptation of the vehicle for the intervention */
    private Timestamp acceptDate;

    /** Date of the first commitment of the vehicle for the intervention */
    private Timestamp commitedDate;

    /** Date of the release of the vehicle from the intervention */
    private Timestamp releasedDate;

    /** Instance of {@link SymbolSitac} for the symbol sitac of the unit */
    private SymbolSitac symbolSitac;

    /**
     * Default constructor.
     */
    public Unit() {

    }


    /**
     * Constructor that instanciate a Unit in an Intervention context,
     * it Will initialize the Request Date and the related UnitVehicle instance
     * @param intervention
     */
    public Unit(Intervention intervention) {
        this.intervention = intervention;
        this.unitVehicle = new UnitVehicle();
        this.requestDate = new Timestamp(new Date().getTime());
    }

    /**
     * Constructor by value.
     *
     * @param intervention the intervention
     * @param unitVehicle  the unitVehicle
     * @param vehicle      the vehicle
     * @param moving       is the unit on the way to its target
     * @param requestDate  the request date
     * @param acceptDate   the acceptation date
     * @param symbolSitac  the symbol
     */
    public Unit(Intervention intervention,
                UnitVehicle unitVehicle,
                Vehicle vehicle,
                boolean moving,
                Timestamp requestDate,
                Timestamp acceptDate,
                SymbolSitac symbolSitac) {
        this.intervention = intervention;
        this.unitVehicle = unitVehicle;
        this.unitVehicle.setAssignedVehicle(vehicle);
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    /**
     * Gets commited date.
     *
     * @return the commited date
     */
    public Timestamp getCommitedDate() {
        return commitedDate;
    }

    /**
     * Sets commited date.
     *
     * @param commitedDate the commited date
     */
    public void setCommitedDate(Timestamp commitedDate) {
        this.commitedDate = commitedDate;
    }

    /**
     * Gets released date.
     *
     * @return the released date
     */
    public Timestamp getReleasedDate() {
        return releasedDate;
    }

    /**
     * Sets est
     * e =released date.
     *
     * @param releasedDate the released date
     */
    public void setReleasedDate(Timestamp releasedDate) {
        this.releasedDate = releasedDate;
    }

    /**
     * Gets symbol sitac.
     *
     * @return the symbol sitac
     */
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    public SymbolSitac getSymbolSitac() {
        return symbolSitac;
    }

    /**
     * Sets symbol sitac.
     *
     * @param symbolSitac the symbol sitac
     */
    public void setSymbolSitac(SymbolSitac symbolSitac) {
        this.symbolSitac = symbolSitac;
    }

    /**
     * Gets unit vehicle.
     *
     * @return the unit vehicle
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @NotNull
    public UnitVehicle getUnitVehicle() {
        return unitVehicle;
    }

    /**
     * Sets unit vehicle.
     *
     * @param unitVehicle the unit vehicle
     */
    public void setUnitVehicle(UnitVehicle unitVehicle) {
        this.unitVehicle = unitVehicle;
    }
}
