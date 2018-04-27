package fr.istic.sit.codisgroupea.constraints.groups;

/**
 * Represent the different Groups Constraint used to validate Messages
 */
public class Message {

    /**
     * Group that permit to the Constraint Validator to check if all needed ID on a Messages are set
     */
    public interface IdAware {}

    /**
     * Group that permit to the Constraint Validator to check if the {@link fr.istic.sit.codisgroupea.model.message.UnitMessage} instance is correctly filled
     */
    public interface UnitMessageReception {}


    /**
     * Group that permit to the Constraint Validator to check if the {@link fr.istic.sit.codisgroupea.model.message.demand.CreateUnitMessage } instance is correctly filled
     */
    public interface CreateUnitMessageReception {}


    /**
     * Group that permit to the Constraint Validator to check if the {@link fr.istic.sit.codisgroupea.model.message.demand.CreateUnitMessage } instance is correctly filled with a Symbol
     */
    public interface CreateUnitMessageWithSymbolReception {}
}
