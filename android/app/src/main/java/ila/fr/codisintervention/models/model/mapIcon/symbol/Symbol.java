package ila.fr.codisintervention.models.model.mapIcon.symbol;


import ila.fr.codisintervention.models.model.mapIcon.Color;
import ila.fr.codisintervention.models.model.InterventionModel;
import ila.fr.codisintervention.models.model.Position;
import ila.fr.codisintervention.models.model.mapIcon.Shape;

/**
 * Graphical symbol placed on the SITAC, it features its location.
 */
public class Symbol {

    /** The id of the symbol sitac */
    private Integer id;

    /** Instance of {@link InterventionModel} for the intervention of the symbol sitac */
    private InterventionModel intervention;

    /** Instance of {@link Position} for the location of the symbol sitac */
    private Position location;

    /** Instance of {@link Payload} for the payload of the symbol sitac */
    private Payload payload;

    /** Instance of {@link Color} for the color of the symbol */
    private Color color;

    /** Instance of {@link Shape} for the shape of the symbol */
    private Shape shape;


}
