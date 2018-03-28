package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 27/03/18.
 */

public class Demande implements Parcelable {

    @Expose
    private int id;

    @Expose
    private Vehicle vehicle;


    protected Demande(Parcel in) {
        id = in.readInt();
        vehicle = in.readParcelable(Vehicle.class.getClassLoader());
    }

    public static final Creator<Demande> CREATOR = new Creator<Demande>() {
        @Override
        public Demande createFromParcel(Parcel in) {
            return new Demande(in);
        }

        @Override
        public Demande[] newArray(int size) {
            return new Demande[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(vehicle, flags);
    }
}
