package ila.fr.codisintervention.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Represent a Location in term of gps coordinate
 * Created by tanaky on 27/03/18.
 */

public class Location implements Parcelable {

    /**
     * The latitude of the gps coordinate
     */
    @Expose
    private double lat;

    /**
     * The latitude of the gps coordinate
     */
    @Expose
    private double lng;

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    protected Location(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
