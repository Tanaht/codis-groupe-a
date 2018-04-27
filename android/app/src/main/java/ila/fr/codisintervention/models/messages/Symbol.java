package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.Location;
import ila.fr.codisintervention.models.model.map_icon.symbol.SymbolUnit;
import lombok.Getter;
import lombok.Setter;

/**
 * Java Object representation of a Symbol in term of JSON message send to and from the server.
 * Created by tanaky on 28/03/18.
 */
@Getter
@Setter
public class Symbol implements Parcelable {

    /**
     * Identifier of the symbol
     */
    @Expose
    private int id;

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

    public Symbol(SymbolUnit symbolUnit) {
        shape = symbolUnit.getShape().name();
        color = symbolUnit.getColor().name();
        location = symbolUnit.getLocation();
    }

    public Symbol(ila.fr.codisintervention.models.model.map_icon.symbol.Symbol symb){
        id = symb.getId();
        shape = symb.getShape().name();
        color = symb.getColor().name();
        location = symb.getLocation();
        payload = new Payload(symb.getPayload());
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

    /**
     * Instantiates a new Symbol from a Parcel (used for Intent based communication).
     *
     * @param in the in
     */
    protected Symbol(Parcel in) {
        id = in.readInt();
        shape = in.readString();
        color = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        payload = in.readParcelable(Payload.class.getClassLoader());
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
