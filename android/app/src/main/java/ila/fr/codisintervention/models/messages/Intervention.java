package ila.fr.codisintervention.models.messages;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.model.InterventionModel;

/**
 * Represent an intervention in terms of JSON Message
 * Created by tanaky on 27/03/18.
 */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
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
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Is drone available boolean.
     *
     * @return the boolean
     */
    public boolean isDrone_available() {
        return drone_available;
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
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
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
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets drone available.
     *
     * @param drone_available the drone available
     */
    public void setDrone_available(boolean drone_available) {
        this.drone_available = drone_available;
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
     * Gets photos.
     *
     * @return the photos
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    /**
     * Sets photos.
     *
     * @param photos the photos
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Instantiates a new Intervention.
     */
    public Intervention() {
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
        photos = in.createTypedArrayList(Photo.CREATOR);
    }

    public Intervention(InterventionModel interventionModel){
        id = interventionModel.getId();
        date = interventionModel.getDate();
        code = interventionModel.getSinisterCode();
        address = interventionModel.getAddress();
        drone_available = true;

        location = new Location(interventionModel.getPosition());
        photos = new ArrayList<>();
        if (interventionModel.getPhotos() != null){
            for (ila.fr.codisintervention.models.model.Photo photo : interventionModel.getPhotos()){
                photos.add(new Photo(photo));
            }
        }
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
    }


}
