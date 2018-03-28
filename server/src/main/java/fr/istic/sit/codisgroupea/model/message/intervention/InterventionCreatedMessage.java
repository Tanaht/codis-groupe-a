package fr.istic.sit.codisgroupea.model.message.intervention;

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
    public Position location;

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
                                      Position location) {
        this.id = id;
        this.date = date;
        this.code = code;
        this.address = address;
        this.droneAvailable = droneAvailable;
        this.location = location;
    }

    public InterventionCreatedMessage(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDroneAvailable() {
        return droneAvailable;
    }

    public void setDroneAvailable(boolean droneAvailable) {
        this.droneAvailable = droneAvailable;
    }

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }
}
