package ila.fr.codisintervention.Entities;

import android.view.View;
import android.widget.ImageView;

import ila.fr.codisintervention.R;

/**
 * Created by christophe on 27/03/18.
 */

public class SymboleDispo {

    private String id;
    private ImageView imageView;
    private String iconeNonSelected;
    private String iconeSelected;
    private boolean selected;

    public SymboleDispo() {
        this.id = "";
        this.imageView = null;
        this.iconeSelected = "";
        this.iconeNonSelected = "";
        this.selected = false;
    }

    public SymboleDispo(String id, String iconeSelected, String iconeNonSelected, boolean selected) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void clicOnSymbol(){
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
