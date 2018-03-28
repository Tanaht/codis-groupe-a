package fr.istic.sit.codisgroupea.model.message.intervention;

/**
 * The create-intervention message.
 */
public class CreateInterventionMessage {
    /**
     * The Code.
     */
    public String code;
    /**
     * The Address.
     */
    public String address;
    /**
     * The Location.
     */
    public Position location;

    /**
     * Instantiates a new create-intervention message.
     *
     * @param code     the code
     * @param address  the address
     * @param location the location
     */
    public CreateInterventionMessage(String code, String address, Position location) {
        this.code = code;
        this.address = address;
        this.location = location;
    }


    /**
     * Instantiates a new Create intervention message.
     */
    public CreateInterventionMessage(){

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

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }
}
