package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.model.Position;

/**
 * Represent a Location in terms of gps coordinate
 * Created by tanaky on 27/03/18.
 */
public class Location implements Parcelable {

    /**
     * The latitude of the gps coordinate
     */
    @Expose
    private double lat;

    /**
     * The longitude of the gps coordinate
     */
    @Expose
    private double lng;

    /**
     * Instantiates a new Location.
     *
     * @param lat the latitude
     * @param lng the longitude
     */
    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Location(Position position) {
        lat = position.getLatitude();
        lng = position.getLongitude();
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * Gets longitude
     *
     * @return the longitude
     */
    public double getLng() {
        return lng;
    }

    /**
     * Sets latitude.
     *
     * @param lat the latitude
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Sets longitude
     *
     * @param lng the longitude
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     * The constant CREATOR.
     */
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

    /**
     * Instantiates a new Location from a parcel.
     * Usefull to unParcelize an instance of this class  {@link Parcelable}
     * @param in the parcel that contain this instance datas
     */
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
