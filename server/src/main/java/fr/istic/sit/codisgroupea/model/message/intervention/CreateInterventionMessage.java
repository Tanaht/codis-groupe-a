package fr.istic.sit.codisgroupea.model.message.intervention;


//{"address":"ISTIC, Bat 12 D, Allée Henri Poincaré, Rennes","code":"INC","location":{"lat":48.1156746,"lng":-1.640608}}

import java.util.List;

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



    public List<Unit> units;

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }
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


    public static class Unit {
        private Integer id;
        private Vehicle vehicle;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
        }


        public static class Vehicle{
            private String label;
            private String type;
            private String status;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
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
