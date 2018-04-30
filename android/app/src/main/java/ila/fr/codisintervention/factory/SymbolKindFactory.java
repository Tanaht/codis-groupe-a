package ila.fr.codisintervention.factory;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.entities.SymbolKind;
import ila.fr.codisintervention.R;

/**
 * Created by christophe on 27/03/18.
 * TODO: The finality is to refactor this Factory (an enum perhaps ?)
 */
public class SymbolKindFactory {

    private SymbolKindFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static List<SymbolKind> getAvailableSymbols() {
        List<SymbolKind> liste = new ArrayList<>();
        liste.add(new SymbolKind("waterresource", R.drawable.bluewaterresource, "water_resource_glow", "water_resource", false));
        liste.add(new SymbolKind("sinister",R.drawable.sinister,"sinister_glow", "sinister", false));
        liste.add(new SymbolKind("dangerdown",R.drawable.greendangerdown,"triangle_down_glow", "triangle_down", false));
        liste.add(new SymbolKind("dangerup", R.drawable.greendangerup, "triangle_up_glow", "triangle_up", false));
        //liste.add(new SymbolKind("vehicle", R.drawable.greenvehicle,"vehicle_glow", "vehicle", false));
        liste.add(new SymbolKind("noteffectivevehicle", R.drawable.bluenoteffectivevehicle, "not_effective_vehicle_glow", "not_effective_vehicle",false));
        //liste.add(new SymbolKind("firevehicle", R.drawable.redfirevehicle, "fire_vehicle_glow", "fire_vehicle", false));
        liste.add(new SymbolKind("noteffectivefirevehicle", R.drawable.rednoteffectivefirevehicle, "not_effective_fire_vehicle_glow", "not_effective_fire_vehicle", false));
        liste.add(new SymbolKind("area", R.drawable.redarea,"area_glow", "area", false));
        liste.add(new SymbolKind("drone", R.drawable.drone_icon_map, "drone_glow", "drone", false));
        return liste;
    }

}
