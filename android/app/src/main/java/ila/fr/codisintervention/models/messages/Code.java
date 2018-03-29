package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 27/03/18.
 */

public class Code implements Parcelable {

    @Expose
    private String label;

    @Expose
    private String description;


    public Code(Parcel in) {
        this.label = in.readString();
        this.description = in.readString();
    }

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
