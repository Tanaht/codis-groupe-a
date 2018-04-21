package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by tanaky on 28/03/18.
 * Represent the payload associated with a Symbol on the map
 */
public class Payload implements Parcelable {

    /**
     * Identifier Payload to show with some symbols
     */
    @Expose
    private String identifier;

    /**
     * Details payload to show with some symbols
     */
    @Expose
    private String details;

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets details.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets details.
     *
     * @param details the details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Instantiates a new Payload.
     *
     * @param in the in
     */
    protected Payload(Parcel in) {
        identifier = in.readString();
        details = in.readString();
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<Payload> CREATOR = new Creator<Payload>() {
        @Override
        public Payload createFromParcel(Parcel in) {
            return new Payload(in);
        }

        @Override
        public Payload[] newArray(int size) {
            return new Payload[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identifier);
        dest.writeString(details);
    }
}
