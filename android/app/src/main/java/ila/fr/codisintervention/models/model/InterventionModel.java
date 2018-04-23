package ila.fr.codisintervention.models.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

import ila.fr.codisintervention.models.messages.Photo;
import ila.fr.codisintervention.models.messages.Symbol;
import ila.fr.codisintervention.models.messages.Unit;
import ila.fr.codisintervention.models.messages.User;

/**
 * Representation of an intervention.
 */
public class InterventionModel {

    /** The id of the intervention */
    private Integer id;

    /** The date of the intervention */
    private long date;

    /** Instance of {@link Position} objet for the intervention */
    private Position position;

    /** Address of the intervention */
    private String address;

    /** Instance of {@link SinisterCode} for the intervention */
    private SinisterCode sinisterCode;
    private boolean opened;

    private List<Photo> listPhoto;


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
     * @param listPhoto       is the list of photo
     */
    public InterventionModel(Integer id, long date, Position position, String address, SinisterCode sinisterCode, boolean opened, List<Photo> listPhoto) {
        this.id = id;
        this.date = date;
        this.position = position;
        this.address = address;
        this.sinisterCode = sinisterCode;
        this.listPhoto = listPhoto;
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
    /**
     * get the list of photo
     *
     * @return the list of photo
     */
    public List<Photo> getListPhoto() {
        return listPhoto;
    }
    /**
     * Sets list of photo
     *
     * @param listPhoto the list of photo
     */
    public void setListPhoto(List<Photo> listPhoto) {
        this.listPhoto = listPhoto;
    }
}
