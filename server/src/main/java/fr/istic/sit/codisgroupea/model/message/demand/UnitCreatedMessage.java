package fr.istic.sit.codisgroupea.model.message.demand;

/**
 * The unit-created message.
 */
public class UnitCreatedMessage {

    /**
     * Instantiates a new Unit created message.
     */
    public UnitCreatedMessage(){

    }

    /**
     * The type Vehicle.
     */
    public static class Vehicle {
        /**
         * The Type.
         */
        public String type;

        /**
         * The Status
         */
        public String status;

        /**
         * Instantiates a new Vehicle.
         *
         * @param type the type
         */
        public Vehicle(String type, String status) {
            this.type = type;
            this.status = status;
        }

        /**
         * Instantiates a new Vehicle.
         */
        public Vehicle(){

        }
    }

    /**
     * The Id.
     */
    public int id;

    /**
     * The date of reservation
     */
    public long date_reserved;
    /**
     * The Vehicle.
     */
    public Vehicle vehicle;

    /**
     * Instantiates a new unit-created message.
     *
     * @param id      the id
     * @param vehicle the vehicle
     */
    public UnitCreatedMessage(int id, long date_reserved, Vehicle vehicle) {
        this.id = id;
        this.date_reserved = date_reserved;
        this.vehicle = vehicle;
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
}
