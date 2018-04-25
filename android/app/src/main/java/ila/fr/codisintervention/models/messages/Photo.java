package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 28/03/18.
 * Represent a snapshot taken by the drone
 */
public class Photo implements Parcelable {

    /**
     * URL of the photo to retrieve through HTTP
     */
    @Expose
    private String url;

    /**
     * Date of the snapshot
     */
    @Expose
    private long date;

    /**
     * Location of the snapshot
     */
    @Expose
    private Location location;

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
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
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(long date) {
        this.date = date;
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
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Instantiates a new Photo.
     *
     * @param in the parcel that contain the details of this class
     */
    protected Photo(Parcel in) {
        url = in.readString();
        date = in.readLong();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    public Photo(ila.fr.codisintervention.models.model.Photo photo){
         url = photo.getUri();
         date = photo.getDate().getTime();
         location = new Location(photo.getCoordinates());
    }

    /**
     * The constant CREATOR.
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     */
    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeLong(date);
        dest.writeParcelable(location, flags);
    }
}
