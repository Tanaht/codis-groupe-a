package fr.istic.sit.codisgroupea.model.message.intervention;


//{"address":"ISTIC, Bat 12 D, Allée Henri Poincaré, Rennes","code":"INC","location":{"lat":48.1156746,"lng":-1.640608}}
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
     * Gets location.
     *
     * @return the location
     */
    public Position getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Position location) {
        this.location = location;
    }
}
