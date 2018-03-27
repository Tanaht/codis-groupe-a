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
    private void addToSig(Color color, Shape shape, Payload payload, Position location) {
        Symbol sym = new Symbol(color, shape);
        symbols.add(new SymbolSitac(null, sym, location, payload));
    }

    @Override
    public SymbolSitac createSymbolFromSigEntry(Intervention intervention, SymbolSitac entry) {
        entry.setIntervention(intervention);
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
