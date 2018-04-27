package ila.fr.codisintervention.models.model.map_icon.symbol;

import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.model.map_icon.Color;
import ila.fr.codisintervention.models.model.map_icon.Shape;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SymbolUnit {

    /** Instance of {@link Location} for the location of the symbol sitac */
    private Location location;
    /** Instance of {@link Color} for the color of the symbol */
    private Color color;
    /** Instance of {@link Shape} for the shape of the symbol */
    private Shape shape;

    public SymbolUnit(ila.fr.codisintervention.models.messages.Symbol symb){
        location = symb.getLocation();
        color = Color.valueOf(symb.getColor());
        shape = Shape.valueOf(symb.getShape());
    }

    public void load(SymbolUnit symbolUnit) {
        location = symbolUnit.getLocation();
        color = symbolUnit.getColor();
        shape = symbolUnit.getShape();
    }
}
