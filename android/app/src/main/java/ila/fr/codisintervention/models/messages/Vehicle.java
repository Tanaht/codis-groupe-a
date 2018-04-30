package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tanaky on 27/03/18.
 * A vehicle with it's status
 */
@Getter
@Setter
public class Vehicle implements Parcelable {

    /**
     * It has a constraint of Uniq Id
     * Label of the vehicle
     */
    @Expose
    private String label;


    /**
     * Type of the vehicle
     * @see Type#label must match with this value.
     */
    @Expose
    private String type;


    /**
     * Status of the vehicle
     */
    @Expose
    private String status;

    /**
     * Instantiates a new Vehicle.
     *
     * @param in the parcel that contain the Vehicle Detail
     */
    protected Vehicle(Parcel in) {
        label = in.readString();
        type = in.readString();
        status = in.readString();
    }

    public Vehicle(ila.fr.codisintervention.models.model.map_icon.vehicle.Vehicle vehicle) {
        this.label = vehicle.getLabel();
        this.status = vehicle.getStatus().name();
        this.type = vehicle.getType();
    }

    /**
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     * The constant CREATOR.
     */
    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.type);
        dest.writeString(this.status);
    }
}
