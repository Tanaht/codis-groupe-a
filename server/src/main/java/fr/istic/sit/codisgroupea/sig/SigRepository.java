package fr.istic.sit.codisgroupea.sig;

import fr.istic.sit.codisgroupea.model.entity.Position;
import fr.istic.sit.codisgroupea.model.entity.Symbol;

import java.util.List;

/**
 * Interface for SIG repositories.
 * This interface enables us to manipulate the SIG without assumption about its structure.
 */
public interface SigRepository {
    /**
     * Create symbol from sig entry symbol.
     *
     * @param entry the entry
     * @return the symbol
     */
    Symbol createSymbolFromSigEntry(SigEntry entry);

    /**
     * Gets entries within rect.
     *
     * @param upperLeft  the upper left corner of the rect
     * @param lowerRight the lower right corner of the rect
     * @return the entries within rect
     */
    List<SigEntry> getEntriesWithinRect(Position upperLeft, Position lowerRight);
}
