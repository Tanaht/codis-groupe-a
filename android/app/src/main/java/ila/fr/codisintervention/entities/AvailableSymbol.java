package ila.fr.codisintervention.entities;

import android.widget.ImageView;

/**
 * FIXME: Delete as soon as possible
 * Created by christophe on 27/03/18.
 * Stub waiting for a true model for this, represent a vehicle
 *
 * This class is not commented because it has to be removed.
 */

public class AvailableSymbol {

    private String id;

    private int idDrawable;
    private ImageView imageView;
    private String iconeNonSelected;
    private String iconeSelected;
    private boolean selected;

    public AvailableSymbol() {
        this.id = "";
        this.imageView = null;
        this.iconeSelected = "";
        this.iconeNonSelected = "";
        this.selected = false;
    }

    public AvailableSymbol(String id, int idDrawable, String iconeSelected, String iconeNonSelected, boolean selected) {
        this.id = id;
        this.idDrawable = idDrawable;
        this.imageView = null;
        this.iconeSelected = iconeSelected;
        this.iconeNonSelected = iconeNonSelected;
        this.selected = selected;
    }


    public String getIcone() {
        return selected ? this.iconeSelected : this.iconeNonSelected;
    }

    public void setIconeNonSelected(String iconeNonSelected) {
        this.iconeNonSelected = iconeNonSelected;
    }

    public void setIconeSelected(String iconeSelected) {
        this.iconeSelected = iconeSelected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public int getIdDrawable() {
        return idDrawable;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public void clicOnSymbol() {
        this.selected = !(this.selected);
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

    public String getIconeSelected() {
        return iconeSelected;
    }
}
