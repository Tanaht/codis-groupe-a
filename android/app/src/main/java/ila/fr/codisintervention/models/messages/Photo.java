package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.Location;

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

    @Expose
    private int interventionId;

    /**
     * Id of the point
     */
    @Expose
    private int pointId;

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
     * Gets interventionId.
     *
     * @return the interventionId
     */
    public int getInterventionId() {
        return interventionId;
    }

    /**
     * Sets interventionId.
     *
     * @param interventionId the interventionId
     */
    public void setInterventionId(int interventionId) {
        this.interventionId = interventionId;
    }

    /**
     * Gets pointId.
     *
     * @return the pointId
     */
    public int getPointId() {
        return pointId;
    }

    /**
     * Sets pointId.
     *
     * @param pointId the pointId
     */
    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    /**
     * Instantiates a new Photo.
     *
     * @param in the parcel that contain the details of this class
     */
    protected Photo(Parcel in) {
        location = in.readParcelable(Location.class.getClassLoader());
        url = in.readString();
        date = in.readLong();
        location = in.readParcelable(Location.class.getClassLoader());
        interventionId = in.readInt();
        pointId = in.readInt();
    }

    public Photo(ila.fr.codisintervention.models.model.Photo photo){
         url = photo.getUri();
         date = photo.getDate().getTime();
         location = photo.getLocation();
         interventionId = photo.getInterventionId();
         pointId = photo.getPointId();
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
        dest.writeParcelable(location, flags);
        dest.writeString(url);
        dest.writeLong(date);
        dest.writeParcelable(location, flags);
        dest.writeInt(interventionId);
        dest.writeInt(pointId);
    }
}
