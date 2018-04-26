package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

import ila.fr.codisintervention.models.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterventionChosenMessage implements Parcelable {

    /**
     * The Id.
     */
    @Expose
    public int id;
    /**
     * The Symbols.
     */
    @Expose
    public List<Symbol> symbols;
    /**
     * The Units.
     */
    @Expose
    public List<Unit> units;
    /**
     * The Photos.
     */
    @Expose
    public List<Photo> photos;

    /**
     * The constant CREATOR.
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     */
    public static final Creator<Intervention> CREATOR = new Creator<Intervention>() {
        @Override
        public Intervention createFromParcel(Parcel in) {
            return new Intervention(in);
        }

        @Override
        public Intervention[] newArray(int size) {
            return new Intervention[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeList(symbols);
        dest.writeList(units);
        dest.writeList(photos);
    }
}