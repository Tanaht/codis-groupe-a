package ila.fr.codisintervention.entities;

/**
 * FIXME: Delete as soon as possible
 * Created by aminesoumiaa on 22/03/18.
 * Stub waiting for a true model for this, represent a vehicle
 *
 * This class is not commented because it has to be removed.
 */
public class Vehicle {

    String code = null;
    String name = null;
    boolean selected = false;

    public Vehicle(String code, String name, boolean selected) {
        super();
        this.code = code;
        this.name = name;
        this.selected = selected;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
