package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 27/03/18.
 */

public class Vehicle implements Parcelable {

    @Expose
    private String label;


    @Expose
    private String type;


    @Expose
    private String status;

    protected Vehicle(Parcel in) {
        label = in.readString();
        type = in.readString();
        status = in.readString();
    }

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
