package ila.fr.codisintervention.models.messages;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.model.InterventionModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Represent an intervention in terms of JSON Message
 * Created by tanaky on 27/03/18.
 */
@Getter
@Setter
public class Intervention implements Parcelable {

    /**
     * uniq Identifier
     */
    @Expose
    private Integer id;

    /**
     * Date of creation
     */
    @Expose
    private long date;

    /**
     * Sinister Code
     */
    @Expose
    private String code;

    /**
     * String address
     */
    @Expose
    private String address;

    /**
     * Warning: Do not rename it must match the key of the json message
     * boolean to know if the drone is available for this intervention
     */
    @Expose
    private boolean drone_available;

    /**
     * Location of the intervention
     */
    @Expose
    private Location location;


    /**
     * Photos related to this intervention
     */
    @Expose
    private List<Photo> photos;


    @Expose
    public List<Symbol> symbols;
    /**
     * The Units.
     */
    @Expose
    public List<Unit> units;

    @Expose
    public PathDrone pathDrone;

    /**
     * Instantiates a new Intervention.
     */
    public Intervention() {
        photos = new ArrayList<>();
        symbols = new ArrayList<>();
        units = new ArrayList<>();
    }
    /**
     * The constant CREATOR.
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     */
    public static final Creator<Intervention> CREATOR = new Creator<Intervention>() {
        @Override
        public Intervention createFromParcel(Parcel in) {
            return new Intervention(in);
        }

        @Override
        public Intervention[] newArray(int size) {
            return new Intervention[size];
        }
    };

    public Intervention(InterventionModel interventionModel){
        id = interventionModel.getId();
        date = interventionModel.getDate();
        code = interventionModel.getSinisterCode();
        address = interventionModel.getAddress();
        drone_available = true;

        location = interventionModel.getLocation();
        photos = new ArrayList<>();
        if (interventionModel.getPhotos() != null){
            for (ila.fr.codisintervention.models.model.Photo photo : interventionModel.getPhotos()){
                photos.add(new Photo(photo));
            }
        }
        symbols = new ArrayList<>();
        if (interventionModel.getSymbols() != null){
            for (ila.fr.codisintervention.models.model.map_icon.symbol.Symbol symb : interventionModel.getSymbols()){
                symbols.add(new Symbol(symb));
            }
        }

        units = new ArrayList<>();
        if (interventionModel.getUnits() != null){
            for (ila.fr.codisintervention.models.model.Unit unit : interventionModel.getUnits()){
                units.add(new Unit(unit));
            }
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * Instantiates a new Intervention.
     *
     * @param in the parcel that contain the details of this class
     */
    protected Intervention(Parcel in) {
        id = in.readInt();
        date = in.readLong();
        code = in.readString();
        address = in.readString();
        drone_available = in.readByte() != 0;

        location = in.readParcelable(Location.class.getClassLoader());
        pathDrone = in.readParcelable(PathDrone.class.getClassLoader());

        photos = in.createTypedArrayList(Photo.CREATOR);
        symbols = in.createTypedArrayList(Symbol.CREATOR);
        units = in.createTypedArrayList(Unit.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(date);
        dest.writeString(code);
        dest.writeString(address);
        dest.writeInt(drone_available ? 1 : 0);

        dest.writeParcelable(location, flags);
        dest.writeParcelable(pathDrone, flags);

        dest.writeTypedList(photos);
        dest.writeTypedList(symbols);
        dest.writeTypedList(units);
    }


}
