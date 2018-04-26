package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by aminesoumiaa on 24/04/18.
 */

public class DronePing implements Parcelable{

    /**
     * Represent a Location in terms of gps coordinate
     */
    @Expose
    private Location location;

    /**
     * The altitude of the gps coordinate
     */
    @Expose
    private double altitude;

    /**
     * Instantiates a new DronePing.
     * @param location
     * @param altitude
     */
    public DronePing(Location location, double altitude) {
        this.location = location;
        this.altitude = altitude;
    }

    /**
     * Gets the location
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the altitude
     * @return the altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Sets the altitude
     * @param altitude the altitude
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     * Instantiates a new DronePing from a parcel.
     * Useful to unparcelize an instance of this class  {@link Parcelable}
     * @param in the parcel that contain this instance data
     */
    protected DronePing(Parcel in) {
        location = in.readParcelable(Location.class.getClassLoader());
        altitude = in.readDouble();
    }

    /**
     * Useful to Parcelize an instance of this class  {@link Parcelable}
     * The constant CREATOR.
     */
    public static final Creator<DronePing> CREATOR = new Creator<DronePing>() {
        @Override
        public DronePing createFromParcel(Parcel in) {
            return new DronePing(in);
        }

        @Override
        public DronePing[] newArray(int size) {
            return new DronePing[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(location, i);
        parcel.writeDouble(altitude);
    }
}
