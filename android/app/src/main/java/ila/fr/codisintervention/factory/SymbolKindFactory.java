package ila.fr.codisintervention.factory;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.entities.SymbolKind;
import ila.fr.codisintervention.R;

/**
 * Created by christophe on 27/03/18.
 */

public class SymbolKindFactory {

    private SymbolKindFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static List<SymbolKind> getAvailableSymbols() {
        List<SymbolKind> liste = new ArrayList<>();
        liste.add(new SymbolKind("ressource_eau", R.drawable.ressourceeneau, "ressource_eau_glow", "ressource_eau", false));
        liste.add(new SymbolKind("sinistre",R.drawable.sinistrerouge,"sinistre_glow", "sinistre", false));
        liste.add(new SymbolKind("triangle_bas",R.drawable.dangervertbas,"triangle_bas_glow", "triangle_bas", false));
        liste.add(new SymbolKind("triangle_haut", R.drawable.dangerverthaut, "triangle_haut_glow", "triangle_haut", false));
        liste.add(new SymbolKind("vehicule", R.drawable.vehiculepompiervert,"vehicule_glow", "vehicule", false));
        liste.add(new SymbolKind("vehicule_non_valide", R.drawable.vehiculebleunonvalide, "vehicule_non_valide_glow", "vehicule_non_valide",false));
        liste.add(new SymbolKind("vehicule_pompier", R.drawable.vehiculepompiervert, "vehicule_pompier_glow", "vehicule_pompier", false));
        liste.add(new SymbolKind("vehicule_pompier_non_valide", R.drawable.vehiculebleunonvalide, "vehicule_pompier_non_valide_glow", "vehicule_pompier_non_valide", false));
        liste.add(new SymbolKind("zone", R.drawable.zoneactionrouge,"zone_glow", "zone", false));
        liste.add(new SymbolKind("drone", R.drawable.drone_icon_map, "drone_glow", "drone", false));
        return liste;
    }

}
