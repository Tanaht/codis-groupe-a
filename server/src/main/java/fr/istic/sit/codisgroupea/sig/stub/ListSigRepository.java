package fr.istic.sit.codisgroupea.sig.stub;

import fr.istic.sit.codisgroupea.model.entity.Position;
import fr.istic.sit.codisgroupea.model.entity.SymbolSitac;
import fr.istic.sit.codisgroupea.sig.SigRepository;

import java.util.ArrayList;
import java.util.List;

public class ListSigRepository implements SigRepository<SymbolSitac> {
    private List<SymbolSitac> symbols;

    public ListSigRepository() {
        symbols = new ArrayList<>();
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
