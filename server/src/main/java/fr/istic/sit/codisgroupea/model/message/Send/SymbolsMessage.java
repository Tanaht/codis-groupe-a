package fr.istic.sit.codisgroupea.model.message.Send;

import fr.istic.sit.codisgroupea.model.message.Receive.SymbolMessage;

/** Class which represent the message of a symbol */
public class SymbolsMessage {

    /** Enum of symbol message type. */
    public enum Type{
        CREATE,
        DELETE,
        UPDATE
    }

    /** The type of the symbol message. */
    private Type type;

    /** The symbol contained by the symbol message. */
    private SymbolMessage symbols;

    /**
     * Constructor of the class {@link SymbolsMessage}.
     * @param type the type of the symbol message.
     * @param symbols the symbol contained by the symbol message.
     */
    public SymbolsMessage(Type type, SymbolMessage symbols) {
        this.type = type;
        this.symbols = symbols;
    }


}
