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
        liste.add(new SymbolKind("water_resource", R.drawable.waterresource, "water_resource_glow", "water_resource", false));
        liste.add(new SymbolKind("sinister",R.drawable.sinister,"sinister_glow", "sinister", false));
        liste.add(new SymbolKind("triangle_down",R.drawable.greendangerdown,"triangle_down_glow", "triangle_down", false));
        liste.add(new SymbolKind("triangle_up", R.drawable.greendangerup, "triangle_up_glow", "triangle_up", false));
        liste.add(new SymbolKind("vehicle", R.drawable.greenvehicle,"vehicle_glow", "vehicle", false));
        liste.add(new SymbolKind("not_effective_vehicle", R.drawable.bluenoteffectivevehicle, "not_effective_vehicle_glow", "not_effective_vehicle",false));
        liste.add(new SymbolKind("fire_vehicle", R.drawable.redfirevehicle, "fire_vehicle_glow", "fire_vehicle", false));
        liste.add(new SymbolKind("not_effective_fire_vehicle", R.drawable.rednoteffectivefirevehicle, "not_effective_fire_vehicle_glow", "not_effective_fire_vehicle", false));
        liste.add(new SymbolKind("area", R.drawable.redarea,"area_glow", "area", false));
        liste.add(new SymbolKind("drone", R.drawable.drone_icon_map, "drone_glow", "drone", false));
        return liste;
    }

}
