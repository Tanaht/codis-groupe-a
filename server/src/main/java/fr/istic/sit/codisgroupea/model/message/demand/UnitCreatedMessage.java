package fr.istic.sit.codisgroupea.model.message.demand;

/**
 * The unit-created message.
 */
public class UnitCreatedMessage {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
