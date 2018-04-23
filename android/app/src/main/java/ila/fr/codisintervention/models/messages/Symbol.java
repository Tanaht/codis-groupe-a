package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.model.Location;

/**
 * Java Object representation of a Symbol in term of JSON message send to and from the server.
 * Created by tanaky on 28/03/18.
 */
public class Symbol implements Parcelable {

    /**
     * Identifier of the symbol
     */
    @Expose
    private Integer id;

    /**
     * Shape of the Symbol it's a String that is one of the one define in enum Shape
     */
    @Expose
    private String shape;

    /**
     * Color of the Symbol it's a String that is one of the one define in enum Color
     */
    @Expose
    private String color;

    /**
     * Location of the symbol on the map
     */
    @Expose
    private Location location;

    /**
     * Payload of the symbol to print along with it on the map.
     */
    @Expose
    private Payload payload;

    /**
     * Instantiates a new Symbol.
     */
    public Symbol() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets shape.
     *
     * @return the shape
     */
    public String getShape() {
        return shape;
    }

    /**
     * Sets shape.
     *
     * @param shape the shape
     */
    public void setShape(String shape) {
        this.shape = shape;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
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
     * Gets payload.
     *
     * @return the payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     * Instantiates a new Symbol from a Parcel (used for Intent based communication).
     *
     * @param in the in
     */
    protected Symbol(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        shape = in.readString();
        color = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        payload = in.readParcelable(Payload.class.getClassLoader());
    }

    /**
     * The constant CREATOR. used for Intent based communication
     */
    public static final Creator<Symbol> CREATOR = new Creator<Symbol>() {
        @Override
        public Symbol createFromParcel(Parcel in) {
            return new Symbol(in);
        }

        @Override
        public Symbol[] newArray(int size) {
            return new Symbol[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shape);
        dest.writeString(color);
        dest.writeParcelable(location, flags);
        dest.writeParcelable(payload, flags);
    }
}
