package ila.fr.codisintervention.Services;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.models.messages.Symbol;

/**
 * Created by christophe on 27/03/18.
 */

public class SymboleDispoService {

    private SymboleDispoService() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Symbol> getListeSymbolesDispo() {
        List<Symbol> liste = new ArrayList<>();
//        liste.add(new SymboleDispo("ressource_eau", "ressource_eau_glow", "ressource_eau", false));
//        liste.add(new SymboleDispo("sinistre","sinistre_glow", "sinistre", false));
//        liste.add(new SymboleDispo("triangle_bas","triangle_bas_glow", "triangle_bas", false));
//        liste.add(new SymboleDispo("triangle_haut","triangle_haut_glow", "triangle_haut", false));
//        liste.add(new SymboleDispo("vehicule","vehicule_glow", "vehicule", false));
//        liste.add(new SymboleDispo("vehicule_non_valide","vehicule_non_valide_glow", "vehicule_non_valide",false));
//        liste.add(new SymboleDispo("vehicule_pompier","vehicule_pompier_glow", "vehicule_pompier", false));
//        liste.add(new SymboleDispo("vehicule_pompier_non_valide","vehicule_pompier_non_valide_glow", "vehicule_pompier_non_valide", false));
//        liste.add(new SymboleDispo("zone","zone_glow", "zone", false));
//        liste.add(new SymboleDispo("drone","drone_glow", "drone", false));
        return liste;
    }

}
