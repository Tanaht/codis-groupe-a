package ila.fr.codisintervention.models.messages;


import ila.fr.codisintervention.models.Location;

/**
 * Represent an intervention in terms of JSON Message
 * Created by tanaky on 27/03/18.
 */
public class Intervention {

    private int id;
    private long date;
    private String code;
    private String address;
    private boolean drone_available;
    private Location location;


    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String getAddress() {
        return address;
    }

    public boolean isDrone_available() {
        return drone_available;
    }

    public Location getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDrone_available(boolean drone_available) {
        this.drone_available = drone_available;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
