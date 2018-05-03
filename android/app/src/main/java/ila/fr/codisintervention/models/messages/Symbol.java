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

    public Symbol(SymbolUnit symbolUnit) {
        if(symbolUnit.getShape() != null)
            shape = symbolUnit.getShape().name();

        if(symbolUnit.getColor() != null)
            color = symbolUnit.getColor().name();

        if(symbolUnit.getLocation() != null)
            location = symbolUnit.getLocation();

        if(symbolUnit.getPayload() != null)
            payload = new Payload(symbolUnit.getPayload());
    }

    public Symbol(ila.fr.codisintervention.models.model.map_icon.symbol.Symbol symb){
        id = symb.getId();
        shape = symb.getShape().name();
        color = symb.getColor().name();
        location = symb.getLocation();
        if (payload != null){
            payload = new Payload(symb.getPayload());
        }
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
        shape = in.readString();
        color = in.readString();
        id = (Integer) in.readValue(Integer.class.getClassLoader());
        location = in.readParcelable(Location.class.getClassLoader());
        payload = in.readParcelable(Payload.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shape);
        dest.writeString(color);
        dest.writeValue(id);
        dest.writeParcelable(location, flags);
        dest.writeParcelable(payload, flags);
    }
}
