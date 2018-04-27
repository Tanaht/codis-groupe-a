package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.model.message.demand.UnitCreatedMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListUnitCreatedMessage {

    /** The type of the list */
    private String type;

    /** The list of Units */
    private List<UnitCreatedMessage> units;

    public ListUnitCreatedMessage(String type, List<UnitCreatedMessage> units) {
        this.type = type;
        this.units = units;
    }
}
