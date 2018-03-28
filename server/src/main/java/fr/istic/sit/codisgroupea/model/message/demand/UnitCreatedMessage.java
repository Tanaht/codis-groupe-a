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
         * Instantiates a new Vehicle.
         *
         * @param type the type
         */
        public Vehicle(String type) {
            this.type = type;
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
     * The Vehicle.
     */
    public Vehicle vehicle;

    /**
     * Instantiates a new unit-created message.
     *
     * @param id      the id
     * @param vehicle the vehicle
     */
    public UnitCreatedMessage(int id, Vehicle vehicle) {
        this.id = id;
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
