package fr.istic.sit.codisgroupea.model.message.send;

import fr.istic.sit.codisgroupea.model.message.receive.SymbolMessage;
import lombok.Data;

import javax.persistence.Enumerated;
import java.util.List;

/** Class which represent the message of a symbol */
@Data
public class SymbolsMessage {

    /** Enum of symbol message type. */
    public enum Type{
        CREATE,
        DELETE,
        UPDATE
    }

    /** The type of the symbol message. */
    @Enumerated
    private Type type;

    /** The symbol contained by the symbol message. */
    private List<SymbolMessage> symbols;

    /**
     * Empty Constructor
     */
    public SymbolsMessage () {}

    /**
     * Constructor of the class {@link SymbolsMessage}.
     * @param type the type of the symbol message.
     * @param symbols the list of symbol contained by the symbol message.
     */
    public SymbolsMessage(Type type, List<SymbolMessage> symbols) {
        this.type = type;
        this.symbols = symbols;
    }


}
