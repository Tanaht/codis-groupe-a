package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tanaky on 27/03/18.
 * Represent a type of vehicle
 */
@Getter
@Setter
public class Type implements Parcelable {

    /**
     * The string representation of the type
     */
    @Expose
    private String label;

    /**
     * Instantiates a new Type.
     *
     * @param in the parcel that contain the details of this class
     */
    public Type(Parcel in) {
        this.label = in.readString();
    }

    /**
     * The constant CREATOR.
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     */
    public static final Creator<Type> CREATOR = new Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
    }
}
