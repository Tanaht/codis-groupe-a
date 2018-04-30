package ila.fr.codisintervention.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.messages.Location;

public class PhotoReception implements Parcelable {

    @Expose
    private String photo;
    @Expose
    private long date;
    @Expose
    private Location location;
    @Expose
    private int interventionId;
    @Expose
    private int pointId;

    public PhotoReception() {
        this.photo = "";
        this.date = 0;
    }

    public PhotoReception(String photo, long date, Location location) {
        this.photo = photo;
        this.date = date;
        this.location = location;
    }

    protected PhotoReception(Parcel in) {
        photo = in.readString();
        date = in.readLong();
        location = in.readParcelable(Location.class.getClassLoader());
        interventionId = in.readInt();
        pointId = in.readInt();
    }

    public static final Creator<PhotoReception> CREATOR = new Creator<PhotoReception>() {
        @Override
        public PhotoReception createFromParcel(Parcel in) {
            return new PhotoReception(in);
        }

        @Override
        public PhotoReception[] newArray(int size) {
            return new PhotoReception[size];
        }
    };

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getInterventionId() {
        return interventionId;
    }

    public void setInterventionId(int interventionId) {
        this.interventionId = interventionId;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(photo);
        parcel.writeLong(date);
        parcel.writeParcelable(location, i);
        parcel.writeInt(interventionId);
        parcel.writeInt(pointId);
    }
}

