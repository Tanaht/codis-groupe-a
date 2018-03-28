package fr.istic.sit.codisgroupea.model.message;

import java.util.List;

/**
 * The type List unit message.
 */
public class ListUnitMessage {

    /** The type of the list */
    private String type;

    /** The list of Units */
    private List<UnitMessage> units;

    /**
     * Instantiates a new List unit message.
     *
     * @param type  the type, CREATE, DELETE or UPDATE
     * @param units the units
     */
    public ListUnitMessage(String type, List<UnitMessage> units) {
        this.type = type;
        this.units = units;
    }
}
