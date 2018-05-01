package fr.istic.sit.codisgroupea.model.message.intervention;

import fr.istic.sit.codisgroupea.model.message.utils.Location;

/**
 * The type  intervention-created message.
 */
public class InterventionCreatedMessage {
    /**
     * The Id.
     */
    public int id;
    /**
     * The Date.
     */
    public long date;
    /**
     * The Code.
     */
    public String code;
    /**
     * The Address.
     */
    public String address;
    /**
     * The Drone available.
     */
    public boolean droneAvailable;
    /**
     * The Location.
     */
    public Location location;

    /**
     * Instantiates a new intervention-created message.
     *
     * @param id             the id
     * @param date           the date
     * @param code           the code
     * @param address        the address
     * @param droneAvailable the drone available
     * @param location       the location
     */
    public InterventionCreatedMessage(int id,
                                      long date,
                                      String code,
                                      String address,
                                      boolean droneAvailable,
                                      Location location) {
        this.id = id;
        this.date = date;
        this.code = code;
        this.address = address;
        this.droneAvailable = droneAvailable;
        this.location = location;
    }

    /**
     * Instantiates a new Intervention created message.
     */
    public InterventionCreatedMessage(){

    }

    /**
     * Gets id.
     *
     * @return the id
     */
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
     * Gets date.
     *
     * @return the date
     */
    public long getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Is drone available boolean.
     *
     * @return the boolean
     */
    public boolean isDroneAvailable() {
        return droneAvailable;
    }

    /**
     * Sets drone available.
     *
     * @param droneAvailable the drone available
     */
    public void setDroneAvailable(boolean droneAvailable) {
        this.droneAvailable = droneAvailable;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
