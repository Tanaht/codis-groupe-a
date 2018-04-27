package fr.istic.sit.codisgroupea.model.message.demand;

import fr.istic.sit.codisgroupea.constraints.groups.Message;
import fr.istic.sit.codisgroupea.model.message.utils.Symbol;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The create-unit message.
 */
@Getter
@Setter
public class CreateUnitMessage {

    /**
     * Default Constructor
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
        @NotNull(groups = {Message.CreateUnitMessageReception.class, Message.CreateUnitMessageWithSymbolReception.class})
        @NotEmpty(groups = {Message.CreateUnitMessageReception.class, Message.CreateUnitMessageWithSymbolReception.class})
        public String type;

        @NotNull(groups = {Message.CreateUnitMessageReception.class, Message.CreateUnitMessageWithSymbolReception.class})
        @NotEmpty(groups = {Message.CreateUnitMessageReception.class, Message.CreateUnitMessageWithSymbolReception.class})
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

    /**
     * The Vehicle.
     */
    @NotNull(groups = {Message.CreateUnitMessageReception.class, Message.CreateUnitMessageWithSymbolReception.class})
    @Valid
    public Vehicle vehicle;

    /**
     * The symbol that locate the vehicle on the map
     */

    public Symbol symbol;

    /**
     * Instantiates a new create-unit message.
     *
     * @param vehicle the vehicle
     */
    public CreateUnitMessage(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}
