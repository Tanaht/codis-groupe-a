package ila.fr.codisintervention.models.messages;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/** Class which represent the message of a symbol */
public class SymbolsMessage implements Parcelable{

    /** The type of the symbol message. */
    @Expose
    private String type;

    /** The symbol contained by the symbol message. */
    @Expose
    private List<Symbol> symbols;

    /**
     * Empty Constructor
     */
    public SymbolsMessage () {}




    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SymbolsMessage> CREATOR = new Creator<SymbolsMessage>() {
        @Override
        public SymbolsMessage createFromParcel(Parcel in) {
            return new SymbolsMessage(in);
        }

        @Override
        public SymbolsMessage[] newArray(int size) {
            return new SymbolsMessage[size];
        }
    };


    public SymbolsMessage(Parcel in) {
        type = in.readString();
        symbols = in.createTypedArrayList(Symbol.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeTypedList(this.symbols);
    }
}