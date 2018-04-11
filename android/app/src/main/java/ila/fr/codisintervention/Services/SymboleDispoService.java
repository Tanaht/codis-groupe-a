package ila.fr.codisintervention.Services;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.entities.SymboleDispo;
import ila.fr.codisintervention.R;

/**
 * Created by christophe on 27/03/18.
 */

public class SymboleDispoService {

    private SymboleDispoService() {
        throw new IllegalStateException("Utility class");
    }

    public static List<SymboleDispo> getListeSymbolesDispo() {
        List<SymboleDispo> liste = new ArrayList<>();
        liste.add(new SymboleDispo("ressource_eau", R.drawable.ressourceeneau, "ressource_eau_glow", "ressource_eau", false));
        liste.add(new SymboleDispo("sinistre",R.drawable.sinistrerouge,"sinistre_glow", "sinistre", false));
        liste.add(new SymboleDispo("triangle_bas",R.drawable.dangervertbas,"triangle_bas_glow", "triangle_bas", false));
        liste.add(new SymboleDispo("triangle_haut", R.drawable.dangerverthaut, "triangle_haut_glow", "triangle_haut", false));
        liste.add(new SymboleDispo("vehicule", R.drawable.vehiculepompiervert,"vehicule_glow", "vehicule", false));
        liste.add(new SymboleDispo("vehicule_non_valide", R.drawable.vehiculebleunonvalide, "vehicule_non_valide_glow", "vehicule_non_valide",false));
        liste.add(new SymboleDispo("vehicule_pompier", R.drawable.vehiculepompiervert, "vehicule_pompier_glow", "vehicule_pompier", false));
        liste.add(new SymboleDispo("vehicule_pompier_non_valide", R.drawable.vehiculebleunonvalide, "vehicule_pompier_non_valide_glow", "vehicule_pompier_non_valide", false));
        liste.add(new SymboleDispo("zone", R.drawable.zoneactionrouge,"zone_glow", "zone", false));
        liste.add(new SymboleDispo("drone", R.drawable.drone_icon_map, "drone_glow", "drone", false));
        return liste;
    }

}
