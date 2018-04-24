package ila.fr.codisintervention.models.model;


import java.util.List;

import ila.fr.codisintervention.models.model.map_icon.drone.PathDrone;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation of an intervention.
 */
@Getter
@Setter
public class InterventionModel {

    /** The id of the intervention */
    private Integer id;

    /** The date of the intervention */
    private long date;

    /** Instance of {@link Position} objet for the intervention */
    private Position position;

    /** Address of the intervention */
    private String address;

    private String sinisterCode;
    private boolean opened;

    private List<Photo> photos;
    private List<Symbol> symbols;
    private List<Unit> units;
    private List<PathDrone> pathDrones;


}
