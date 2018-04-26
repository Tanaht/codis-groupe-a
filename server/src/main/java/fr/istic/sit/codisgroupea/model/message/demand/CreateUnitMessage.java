package fr.istic.sit.codisgroupea.model.message.demand;

import fr.istic.sit.codisgroupea.model.message.utils.Location;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
        @NotNull
        @NotEmpty
        public String type;

        @NotNull
        @NotEmpty
        public String status;

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


    public static class Symbol {
        /**
         * The location of the symbol
         */
        @NotNull
        public Location location;

        /**
         * The shape of the symbol
         */
        @NotEmpty
        public String color;

    }

    /**
     * The Vehicle.
     */
    @NotNull
    public Vehicle vehicle;

    /**
     * The symbol that locate the vehicle on the map
     */
    public Symbol symbol;

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
