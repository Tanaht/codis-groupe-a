package ila.fr.codisintervention.models.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.Photo;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.Unit;

/**
 * Created by marzin on 29/03/18.
 * TODO: To Refactor Ugly copy paste of the class {@link Intervention}, an Intervension choosed class is just an instance of an Intervention
 */
public class InterventionChosen implements Parcelable {

    @Expose
    private int id;

    @Expose
    private long date;

    @Expose
    private String code;

    @Expose
    private String address;

    @Expose
    private boolean droneAvailable;

    @Expose
    private List<Unit> units;

    @Expose
    private List<Symbol> symbols;

    @Expose
    private Location location;


    @Expose
    private List<Photo> photos;


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public long getDate() {
        return date;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Is drone available boolean.
     *
     * @return the boolean
     */
    public boolean isDroneAvailable() {
        return droneAvailable;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets drone available.
     *
     * @param droneAvailable the drone available
     */
    public void setDroneAvailable(boolean droneAvailable) {
        this.droneAvailable = droneAvailable;
    }

    /**
     * Gets units.
     *
     * @return the units
     */
    public List<Unit> getUnits() {
        return units;
    }

    /**
     * Sets units.
     *
     * @param units the units
     */
    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    /**
     * Gets symbols.
     *
     * @return the symbols
     */
    public List<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * Sets symbols.
     *
     * @param symbols the symbols
     */
    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     * The constant CREATOR.
     */
    public static final Creator<InterventionChosen> CREATOR = new Creator<InterventionChosen>() {
        @Override
        public InterventionChosen createFromParcel(Parcel in) {
            return new InterventionChosen(in);
        }

        @Override
        public InterventionChosen[] newArray(int size) {
            return new InterventionChosen[size];
        }
    };

    /**
     * Gets photos.
     *
     * @return the photos
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    /**
     * Sets photos.
     *
     * @param photos the photos
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Instantiates a new Intervention chosen.
     */
    public InterventionChosen() {
    }


    /**
     * Instantiates a new Intervention chosen.
     *
     * @param in the in
     */
    protected InterventionChosen(Parcel in) {
        id = in.readInt();
        date = in.readLong();
        code = in.readString();
        address = in.readString();
        droneAvailable = in.readByte() != 0;
        photos = in.createTypedArrayList(Photo.CREATOR);
        symbols = in.createTypedArrayList(Symbol.CREATOR);
        units = in.createTypedArrayList(Unit.CREATOR);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(date);
        dest.writeString(code);
        dest.writeString(address);
        dest.writeInt(droneAvailable ? 1 : 0);
        dest.writeParcelable(location, flags);
        dest.writeList(photos);
        dest.writeList(units);
        dest.writeList(symbols);
    }

    /**
     * Create by intervention with all intervention chosen.
     *
     * @param intervention the intervention
     * @param symbols      the symbols
     * @param units        the units
     * @return the intervention chosen
     */
    public static InterventionChosen createByInterventionWithAll (Intervention intervention, List<Symbol> symbols, List<Unit> units){
        InterventionChosen interventionChosen = createByIntervention(intervention);
        interventionChosen.setUnits(units);
        interventionChosen.setSymbols(symbols);

        return interventionChosen;
    }

    /**
     * Create by intervention intervention chosen.
     *
     * @param intervention the intervention
     * @return the intervention chosen
     */
    public static InterventionChosen createByIntervention (Intervention intervention){
        InterventionChosen interventionChosen = new InterventionChosen();

        interventionChosen.setAddress(intervention.getAddress());
        interventionChosen.setCode(intervention.getCode());
        interventionChosen.setDate(intervention.getDate());
        interventionChosen.setDroneAvailable(intervention.isDrone_available());
        interventionChosen.setId(intervention.getId());
        interventionChosen.setLocation(intervention.getLocation());
        interventionChosen.setPhotos(intervention.getPhotos());

        return interventionChosen;
    }

    /**
     * Change symbol.
     *
     * @param symbolChanged the symbol changed
     */
    public void changeSymbol(Symbol symbolChanged){
        for(Symbol symbol : this.getSymbols()){
            if(symbol.getId().equals(symbolChanged.getId())){
                symbol = symbolChanged;
            }
        }
    }

    /**
     * Get symbol id symbol.
     *
     * @param id the id
     * @return the symbol
     */
    public Symbol getSymbolId(int id){
        for(Symbol symbol : this.getSymbols()){
            if(symbol.getId().equals(id)){
                return symbol;
            }
        }
        return null;
    }

    /**
     * Delete symbol by id.
     *
     * @param id the id
     */
    public void deleteSymbolById(int id){
        if(id!=-1) {
            for (Symbol symbol : this.getSymbols()) {
                if (symbol.getId().equals(id)) {
                    this.symbols.remove(symbol);
                }
            }
        }
    }

    /**
     * Change unit.
     *
     * @param unitChanged the unit changed
     */
    public void changeUnit(Unit unitChanged){
        for(Unit unit : this.getUnits()){
            if(unit.getId()==(unitChanged.getId())){
                unit = unitChanged;
            }
        }
    }

    /**
     * Get unit by id unit.
     *
     * @param id the id
     * @return the unit
     */
    public Unit getUnitById(int id){
        for(Unit unit : this.getUnits()){
            if(unit.getId()==id){
                return unit;
            }
        }
        return null;
    }
}

