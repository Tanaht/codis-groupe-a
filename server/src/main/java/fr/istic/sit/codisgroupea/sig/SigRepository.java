package fr.istic.sit.codisgroupea.sig;

import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Position;
import fr.istic.sit.codisgroupea.model.entity.SymbolSitac;

import java.util.List;

/**
 * Interface for SIG repositories.
 * This interface enables us to manipulate the SIG without assumption about its structure.
 */
public interface SigRepository<T extends SigEntry> {
    /**
     * Create symbol from sig entry symbol.
     *
     * @param intervention the intervention
     * @param entry the entry
     * @return the symbol
     */
    SymbolSitac createSymbolFromSigEntry(Intervention intervention, T entry);

    /**
     *
     * @param intervention
     * @return
     */
    List<T> getSymbolsInTheIntervention(Intervention intervention);
}
