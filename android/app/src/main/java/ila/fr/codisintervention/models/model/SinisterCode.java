package ila.fr.codisintervention.models.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Encapsulation of the sinister code of an intervention.
 */
public class SinisterCode implements Parcelable {

    /** The id of the sinister code */
    private Integer id;

    /** The code of the sinister code */
    private String code;

    /**
     * Default constructor.
     */
    public SinisterCode() {

    }

    /**
     * Constructor by value.
     *
     * @param code the code
     */
    public SinisterCode(String code) {
        this.code = code;
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
     * Getter of the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter of the code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    protected SinisterCode(Parcel in) {
        id = in.readInt();
        code = in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
    }

    /**
     * Usefull to Parcelize an instance of this class  {@link Parcelable}
     * The constant CREATOR.
     */
    public static final Creator<SinisterCode> CREATOR = new Creator<SinisterCode>() {
        @Override
        public SinisterCode createFromParcel(Parcel in) {
            return new SinisterCode(in);
        }

        @Override
        public SinisterCode[] newArray(int size) {
            return new SinisterCode[size];
        }
    };
}

