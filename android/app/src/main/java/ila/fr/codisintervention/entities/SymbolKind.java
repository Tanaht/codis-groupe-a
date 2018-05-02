package ila.fr.codisintervention.entities;

import android.widget.ImageView;

import ila.fr.codisintervention.models.model.map_icon.Color;
import ila.fr.codisintervention.models.model.map_icon.Shape;

/**
 * FIXME: Refactor as soon as possible -> It can be a lot better
 * FIXME: Why do we not using an Enum for that ? THAT is the question
 * Created by christophe on 27/03/18.
 * <p>
 * Represent a kind of symbol that can be draw on map
 * This class is used to show a button to select kind of symbol to place on map
 */
public class SymbolKind {
    /**
     * kind of symbol registered as a string -> we do not have unicity constraint with this it would be better to use enum.
     */
    private String id;

    /**
     * id of the icon used for the button
     */
    private int idDrawable;

    /**
     * imageView that is used to get the icon from idDrawable resource integer.
     */
    private ImageView imageView;
    private String defaultIcon;
    private String selectedIcon;

    private String color;

    /**
     * boolean used to know if this "tool" is selected to draw symbol on map
     */
    private boolean selected;

    /**
     * Constructor used in {@link ila.fr.codisintervention.factory.SymbolKindFactory}
     *
     * @param id           kind of symbol
     * @param idDrawable   id of the resource icon to use
     * @param selectedIcon icon to use when this "tool" instance is selected
     * @param defaultIcon  default icon to use
     * @param selected     whether or not this "tool" instance is in use
     */
    public SymbolKind(String id, int idDrawable, String selectedIcon, String defaultIcon, boolean selected) {
        this.id = id;
        this.idDrawable = idDrawable;
        this.imageView = null;
        this.selectedIcon = selectedIcon;
        this.defaultIcon = defaultIcon;
        this.selected = selected;
    }


    /**
     * Return the icon to display in the button that is represented by this instance of SymbolKind.
     *
     * @return the current icon according to boolean self.selected value.
     */
    public String getIcon() {
        return selected ? this.selectedIcon : this.defaultIcon;
    }

    /**
     * Sets default icon.
     *
     * @param defaultIcon the default icon
     */
    public void setDefaultIcon(String defaultIcon) {
        this.defaultIcon = defaultIcon;
    }

    /**
     * Sets selected icon.
     *
     * @param selectedIcon the selected icon
     */
    public void setSelectedIcon(String selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    /**
     * Is selected boolean.
     *
     * @return the boolean
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets selected.
     *
     * @param selected the selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }


    /**
     * Gets id drawable.
     *
     * @return the id drawable
     */
    public int getIdDrawable() {
        return idDrawable;
    }

    /**
     * Sets id drawable.
     *
     * @param idDrawable the id drawable
     */
    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    /**
     * Clic on symbol.
     */
    public void clicOnSymbol() {
        this.selected = !(this.selected);
    }

    /**
     * Gets image view.
     *
     * @return the image view
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Sets image view of the current icon.
     *
     * @param imageView the image view
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * Gets default icon.
     *
     * @return the default icon
     */
    public String getDefaultIcon() {
        return defaultIcon;
    }

    /**
     * Gets selected icon.
     *
     * @return the selected icon
     */
    public String getSelectedIcon() {
        return selectedIcon;
    }

    /**
     * Gets selected color.
     *
     * @return the selected color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color of the current icon.
     *
     * @param color the image view
     */
    public void setColor(String color) {
        this.color = color;
    }
}
