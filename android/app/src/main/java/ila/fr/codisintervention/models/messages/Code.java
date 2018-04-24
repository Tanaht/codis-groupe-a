package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tanaky on 27/03/18.
 * Represent a SinisterCode in terms of JSON Message
 */
@Getter
@Setter
public class Code implements Parcelable {

    /**
     * String representation of the Sinister Code
     */
    @Expose
    private String label;

    /**
     * Description of the SinisterCode
     */
    @Expose
    private String description;


    /**
     * Constructor that require a Parcel to instanciate a Code Object
     * @param in the parcel that contain the details of this class
     */
    public Code(Parcel in) {
        this.label = in.readString();
        this.description = in.readString();
    }

    /**
     * The constant CREATOR.
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     */
    public static final Creator<Code> CREATOR = new Creator<Code>() {
        @Override
        public Code createFromParcel(Parcel in) {
            return new Code(in);
        }

        @Override
        public Code[] newArray(int size) {
            return new Code[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.description);
    }


}
