package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 28/03/18.
 */

public class Unit implements Parcelable {

    @Expose
    private int id;

    @Expose
    private long date_granted;

    @Expose
    private long date_reserved;

    @Expose
    private boolean moving;

    @Expose
    private Vehicle vehicle;

    @Expose
    private Symbol symbol;


    protected Unit(Parcel in) {
        id = in.readInt();
        date_granted = in.readLong();
        date_reserved = in.readLong();
        moving = in.readByte() != 0;
        vehicle = in.readParcelable(Vehicle.class.getClassLoader());
    }

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
}
