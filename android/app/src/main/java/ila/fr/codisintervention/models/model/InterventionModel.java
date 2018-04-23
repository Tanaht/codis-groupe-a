package ila.fr.codisintervention.models.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.messages.Photo;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.Unit;
import ila.fr.codisintervention.models.messages.User;

/**
 * Representation of an intervention.
 */
public class InterventionModel implements Parcelable {

    /** The id of the intervention */
    @Expose
    private Integer id;

    /** The date of the intervention */
    @Expose
    private long date;

    /** Instance of {@link Position} objet for the intervention */
    @Expose
    private Position position;

    /** Address of the intervention */
    @Expose
    private String address;

    /** Instance of {@link SinisterCode} for the intervention */
    @Expose
    private SinisterCode sinisterCode;
    private boolean opened;


    /**
     * Default constructor.
     */
    public InterventionModel() {
    }

    /**
     * Constructor by value.
     *
     * @param date         the date of the intervention
     * @param position     the location of the intervention
     * @param address      the address of the intervention
     * @param sinisterCode the sinister code
     * @param opened       is the intervention opened
     */
    public InterventionModel(Integer id, long date, Position position, String address, SinisterCode sinisterCode, boolean opened) {
        this.id = id;
        this.date = date;
        this.position = position;
        this.address = address;
        this.sinisterCode = sinisterCode;
    }

    /**
     * Getter of ID.
     *
     * @return the ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of ID.
     *
     * @param id the ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the date.
     *
     * @return the date
     */
    public long getDate() {
        return date;
    }

    /**
     * Setter of the date.
     *
     * @param date the date
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * Getter of the position.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter of the position.
     *
     * @param position the position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Getter of the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter of the sinister code.
     *
     * @return the sinister code
     */
    public SinisterCode getSinisterCode() {
        return sinisterCode;
    }

    /**
     * Setter of the sinister code.
     *
     * @param sinisterCode the sinister code
     */
    public void setSinisterCode(SinisterCode sinisterCode) {
        this.sinisterCode = sinisterCode;
    }

    /**
     * Is the intervention opened.
     *
     * @return the boolean
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * Sets opened.
     *
     * @param opened the opened
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    protected InterventionModel(Parcel in) {
        id = in.readInt();
        date = in.readLong();
        sinisterCode = in.readParcelable(SinisterCode.class.getClassLoader());
        address = in.readString();
        position = in.readParcelable(Position.class.getClassLoader());
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(date);
        dest.writeParcelable(sinisterCode, flags);
        dest.writeString(address);
        dest.writeParcelable(position, flags);
    }

    /**
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     * The constant CREATOR.
     */
    public static final Creator<InterventionModel> CREATOR = new Creator<InterventionModel>() {
        @Override
        public InterventionModel createFromParcel(Parcel in) {
            return new InterventionModel(in);
        }

        @Override
        public InterventionModel[] newArray(int size) {
            return new InterventionModel[size];
        }
    };
}
