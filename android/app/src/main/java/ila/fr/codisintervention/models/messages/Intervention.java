package ila.fr.codisintervention.models.messages;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.Location;

/**
 * Represent an intervention in terms of JSON Message
 * Created by tanaky on 27/03/18.
 */
public class Intervention implements Parcelable {

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
    private Location location;


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

    public void setLocation(Location location) {
        this.location = location;
    }

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

    public Intervention() {
    }

    protected Intervention(Parcel in) {
        id = in.readInt();
        date = in.readLong();
        code = in.readString();
        address = in.readString();
        drone_available = in.readByte() != 0;
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
    }
}
