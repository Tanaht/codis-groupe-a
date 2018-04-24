package ila.fr.codisintervention.models.model.map_icon.symbol;


import ila.fr.codisintervention.models.model.map_icon.Color;
import ila.fr.codisintervention.models.model.InterventionModel;
import ila.fr.codisintervention.models.model.Position;
import ila.fr.codisintervention.models.model.map_icon.Shape;
import lombok.Getter;
import lombok.Setter;

/**
 * Graphical symbol placed on the SITAC, it features its location.
 */
@Getter
@Setter
public class Symbol {

    /** The id of the symbol sitac */
    private Integer id;

    /** Instance of {@link Position} for the location of the symbol sitac */
    private Position location;

    /** Instance of {@link Payload} for the payload of the symbol sitac */
    private Payload payload;

    /** Instance of {@link Color} for the color of the symbol */
    private Color color;

    /** Instance of {@link Shape} for the shape of the symbol */
    private Shape shape;

    public void load(Symbol symb){
        color = symb.color;
        shape = symb.shape;
        payload.load(symb.getPayload());
        location.load(symb.getLocation());

    }

}
