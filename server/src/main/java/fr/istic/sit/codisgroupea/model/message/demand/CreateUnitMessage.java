package fr.istic.sit.codisgroupea.model.message.demand;

/**
 * The create-unit message.
 */
public class CreateUnitMessage {

    /**
     * Instantiates a new Create unit message.
     */
    public CreateUnitMessage(){

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
     * The Vehicle.
     */
    public Vehicle vehicle;

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

    /**
     * Instantiates a new create-unit message.
     *
     * @param vehicle the vehicle
     */
    public CreateUnitMessage(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}
