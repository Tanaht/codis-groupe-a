package fr.istic.sit.codisgroupea.sig.stub;

import fr.istic.sit.codisgroupea.model.entity.*;
import fr.istic.sit.codisgroupea.sig.SigRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.*;

/**
 * List SIG repository.
 */
@Service
public class ListSigService implements SigRepository<SymbolSitac> {
    private List<SymbolSitac> symbols;

    @Value("${bouchon.sizeFromInterventionCenter}")
    private int sizeFromTheInterventionCenter;

    /**
     * Instantiates a new List SIG repository.
     */
    public ListSigService() {
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
    public List<SymbolSitac> getSymbolsInTheIntervention(Intervention intervention){
        List<SymbolSitac> listSymbolBouchon = new ArrayList<>();


        List<SymbolSitac> listSymbAssociateWithIntervention = new ArrayList<>();
        for (SymbolSitac symbolSitac : listSymbolBouchon){

            SymbolSitac copySymbolSitac = new SymbolSitac();

            copySymbolSitac.setLocation(symbolSitac.getLocation());
            copySymbolSitac.setSymbol(symbolSitac.getSymbol());
            copySymbolSitac.setId(symbolSitac.getId());
            copySymbolSitac.setPayload(symbolSitac.getPayload());

            copySymbolSitac.setIntervention(intervention);

            listSymbAssociateWithIntervention.add(copySymbolSitac);
        }


        return listSymbAssociateWithIntervention;
    }

    /**
     *Method to get the distance in meters between two positions
     *
     * @param pos1 The fist position
     * @param pos2 The second position
     * @return The distance in meters
     */
    private double getMeters(Position pos1, Position pos2) {
        //Radius of the Earth
        double r = 6371 * pow(10, 3);

        //Coordinates of the first point
        double lat1 = toRadians(pos1.getLatitude());
        double lng1 = toRadians(pos1.getLongitude());

        //Coordinates of the first point
        double lat2 = toRadians(pos2.getLongitude());
        double lng2 = toRadians(pos2.getLongitude());

        //Difference between each coordinates
        double diff1 = lat2 - lat1;
        double diff2 = lng2 - lng1;

        //Partial Result
        double partialResult1 = sin(diff1/2) * sin(diff2/2) +
                cos(lat1) * cos(lat2) * sin(diff2) * sin(diff2);
        double partialResult2 = atan2(sqrt(partialResult1), sqrt(1 - partialResult1));

        //Result
        return  r * partialResult2;
    }
}
