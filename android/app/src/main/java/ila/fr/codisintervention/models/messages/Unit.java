package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 28/03/18.
 * A Unit is a Vehicle inside of an Intervention
 */
public class Unit implements Parcelable {

    /**
     * Uniq Identifier
     */
    @Expose
    private int id;

    /**
     * Date of the acceptation of this unit
     * Warning: Do not rename it must match the key of the json message
     */
    @Expose
    private long date_granted;

    /**
     * Date of the reservation of this unit
     * Warning: Do not rename it must match the key of the json message
     */
    @Expose
    private long date_reserved;

    /**
     * Boolean to now if unit is in movement
     */
    @Expose
    private boolean moving;

    /**
     * underlying Vehicle instance
     */
    @Expose
    private Vehicle vehicle;

    /**
     * Symbol instance that represent the Unit in the Map
     */
    @Expose
    private Symbol symbol;


    /**
     * Instantiates a new Unit.
     *
     * @param in the parcel that contain the details of this class
     */
    protected Unit(Parcel in) {
        id = in.readInt();
        date_granted = in.readLong();
        date_reserved = in.readLong();
        moving = in.readByte() != 0;
        vehicle = in.readParcelable(Vehicle.class.getClassLoader());
    }

    /**
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     * The constant CREATOR.
     */
    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(date_granted);
        dest.writeLong(date_reserved);
        dest.writeInt(moving ? 1 : 0);
        dest.writeParcelable(vehicle, flags);
//        dest.writeParcelable(symbol, flags);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }
}
