package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.Location;

/**
 * Created by tanaky on 28/03/18.
 */

public class Symbol implements Parcelable {

    @Expose
    private Integer id;

    @Expose
    private String shape;

    @Expose
    private String color;

    @Expose
    private Location location;

    @Expose
    private Payload payload;

    @Expose
    private boolean selected;

    private ImageView imageView;

    private String iconeNonSelected;
    private String iconeSelected;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

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


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public String getIconeNonSelected() {
        return iconeNonSelected;
    }

    public void setIconeNonSelected(String iconeNonSelected) {
        this.iconeNonSelected = iconeNonSelected;
    }

    public String getIconeSelected() {
        return iconeSelected;
    }

    public void setIconeSelected(String iconeSelected) {
        this.iconeSelected = iconeSelected;
    }
}
