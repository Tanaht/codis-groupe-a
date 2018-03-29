package ila.fr.codisintervention.models;

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
    private boolean drone_available;

    @Expose
    private List<Unit> units;

    @Expose
    private List<Symbol> symbols;

    @Expose
    private Location location;


    @Expose
    private List<Photo> photos;


    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String getAddress() {
        return address;
    }

    public boolean isDrone_available() {
        return drone_available;
    }

    public Location getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDrone_available(boolean drone_available) {
        this.drone_available = drone_available;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static final Creator<ila.fr.codisintervention.models.InterventionChosen> CREATOR = new Creator<ila.fr.codisintervention.models.InterventionChosen>() {
        @Override
        public ila.fr.codisintervention.models.InterventionChosen createFromParcel(Parcel in) {
            return new ila.fr.codisintervention.models.InterventionChosen(in);
        }

        @Override
        public ila.fr.codisintervention.models.InterventionChosen[] newArray(int size) {
            return new ila.fr.codisintervention.models.InterventionChosen[size];
        }
    };

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public InterventionChosen() {
    }


    protected InterventionChosen(Parcel in) {
        id = in.readInt();
        date = in.readLong();
        code = in.readString();
        address = in.readString();
        drone_available = in.readByte() != 0;
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
        dest.writeInt(drone_available ? 1 : 0);
        dest.writeParcelable(location, flags);
        dest.writeList(photos);
        dest.writeList(units);
        dest.writeList(symbols);
    }
    public InterventionChosen createByIntervention (Intervention intervention, List<Symbol> symbols, List<Unit> units){
        InterventionChosen interventionChosen = new InterventionChosen();

        interventionChosen.setAddress(intervention.getAddress());
        interventionChosen.setCode(intervention.getCode());
        interventionChosen.setDate(intervention.getDate());
        interventionChosen.setDrone_available(intervention.isDrone_available());
        interventionChosen.setId(intervention.getId());
        interventionChosen.setLocation(intervention.getLocation());
        interventionChosen.setPhotos(intervention.getPhotos());
        interventionChosen.setUnits(units);
        interventionChosen.setSymbols(symbols);

        return interventionChosen;
    }
}

