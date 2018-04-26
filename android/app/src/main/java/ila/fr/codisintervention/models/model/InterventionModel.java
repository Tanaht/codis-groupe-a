package ila.fr.codisintervention.models.model;


import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.exception.SymbolNotFoundException;
import ila.fr.codisintervention.exception.UnitNotFoundException;
import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.model.map_icon.drone.PathDrone;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import lombok.Setter;
import lombok.Getter;

/**
 * Representation of an intervention.
 */
@Getter
@Setter
public class InterventionModel {

    /** The id of the intervention */
    private Integer id;

    /** The date of the intervention */
    private long date;

    /** Instance of {@link Location} objet for the intervention */
    private Location location;

    /** Address of the intervention */
    private String address;

    private String sinisterCode;

    private boolean opened;

    private List<Photo> photos;
    private List<Symbol> symbols;
    private List<Unit> units;
    private List<PathDrone> pathDrones;


    /**
     * Instantiates a new Intervention model from intervention message.
     *
     * @param intervention the intervention
     */
    public InterventionModel (Intervention intervention){
        this.setAddress(intervention.getAddress());
        this.setDate(intervention.getDate());

        if (intervention.getLocation() != null){
            this.setLocation(intervention.getLocation());
        }
        this.setSinisterCode(intervention.getCode());
        this.setOpened(true);
        if(intervention.getPhotos() != null){
            this.setPhotos(setListPhotoFromMessage(intervention));
        }


        this.setPathDrones(null);
        this.setSymbols(null);
        this.setId(intervention.getId());
    }

    public InterventionModel() {

    }



    private List<Photo> setListPhotoFromMessage (Intervention intervention){
        photos = new ArrayList<>();
        for(ila.fr.codisintervention.models.messages.Photo photo : intervention.getPhotos()){
            Photo photoModel = new Photo(photo);
            photos.add(photoModel);
        }
        return photos;
    }

    /**
     * Add symbol into the symbolList
     * @param symb to add
     */
    public void createSymbol(Symbol symb){
        symbols.add(symb);
    }

    /**
     * Gets symbol.
     *
     * @param idSymb the id symb
     * @return the symbol
     * @throws SymbolNotFoundException the symbol not found exception
     */
    public Symbol getSymbol(int idSymb) throws SymbolNotFoundException {
        for (Symbol symb : symbols){
            if (symb.getId().equals(idSymb)){
                return symb;
            }
        }
        throw new SymbolNotFoundException(idSymb);
    }


    /**
     * Update symbol.
     *
     * @param symbol the symbol
     * @throws SymbolNotFoundException the symbol not found exception
     */
    public void updateSymbol(Symbol symbol) throws SymbolNotFoundException {
        for (Symbol symb : symbols){
            if (symb.getId().equals(symbol.getId())){
                symb.load(symbol);
                return;
            }
        }
        throw new SymbolNotFoundException(symbol.getId());
    }

    /**
     * Delete symbol by id.
     *
     * @param idSymbol the id symbol
     * @throws SymbolNotFoundException the symbol not found exception
     */
    public void deleteSymbolById(int idSymbol) throws SymbolNotFoundException {
        for (int i=0; i < symbols.size();i++){
            if (symbols.get(i).getId().equals(idSymbol)){
                symbols.remove(i);
                return;
            }
        }
        throw new SymbolNotFoundException(idSymbol);
    }

    /**
     * add unit to unitList
     * @param unit unit to add
     */
    public void createUnit(Unit unit){
        units.add(unit);
    }

    /**
     * Gets unit.
     *
     * @param idUnit the id unit
     * @return the unit
     * @throws UnitNotFoundException the unit not found exception
     */
    public Unit getUnit(int idUnit) throws UnitNotFoundException {
        for (Unit unit: units){
            if (unit.getId().equals(idUnit)){
                return unit;
            }
        }
        throw new UnitNotFoundException(idUnit);
    }

    /**
     * Change unit.
     *
     * @param unitUpdated the unit updated
     * @throws UnitNotFoundException the unit not found exception
     */
    public void updateUnit(Unit unitUpdated) throws UnitNotFoundException {
        for(Unit unit : units){
            if(unit.getId().equals(unitUpdated.getId())){
                unit.load(unitUpdated);
                return;
            }
        }
        throw new UnitNotFoundException(unitUpdated.getId());
    }

}
