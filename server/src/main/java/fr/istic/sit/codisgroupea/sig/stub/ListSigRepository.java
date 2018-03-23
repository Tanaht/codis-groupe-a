package fr.istic.sit.codisgroupea.sig.stub;

import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.sig.SigRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * List SIG repository.
 */
public class ListSigRepository implements SigRepository<SymbolSitac> {
    private List<SymbolSitac> symbols;

    /**
     * Instantiates a new List SIG repository.
     */
    public ListSigRepository() {
        symbols = new ArrayList<>();

        addToSig(Color.BLUE, Shape.CIRCLE, null, new Position(48.11540, -1.63813));
        addToSig(Color.ORANGE, Shape.TRIANGLEDOWN, null, new Position(48.11520, -1.63999));
    }

    /**
     * Create a SymbolSitac and add it to the SIG.
     *
     * @param color the color
     * @param shape the shape
     * @param payload the payload
     * @param location the location
     */
    private void addToSig(Color color, Shape shape, String payload, Position location) {
        Symbol sym = new Symbol(color, shape, payload);
        symbols.add(new SymbolSitac(sym, location));
    }

    @Override
    public SymbolSitac createSymbolFromSigEntry(SymbolSitac entry) {
        return entry;
    }

    @Override
    public List<SymbolSitac> getEntriesWithinRect(Position upperLeft, Position lowerRight) {
        List<SymbolSitac> syms = new ArrayList<>();

        for(SymbolSitac sym : symbols) {
            Position pos = sym.getLocation();

            if(upperLeft.getLongitude() <= pos.getLongitude()
                    && lowerRight.getLatitude() <= pos.getLatitude()
                    && pos.getLongitude() <= lowerRight.getLongitude()
                    && pos.getLatitude() <= upperLeft.getLatitude()) {
                syms.add(sym);
            }
        }

        return syms;
    }
}
