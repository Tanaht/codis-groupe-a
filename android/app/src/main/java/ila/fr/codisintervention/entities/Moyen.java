package ila.fr.codisintervention.entities;

/**
 * Created by aminesoumiaa on 22/03/18.
 */

public class Moyen {
    String code = null;
    String name = null;
    boolean selected = false;

    public Moyen(String code, String name, boolean selected) {
        super();
        this.code = code;
        this.name = name;
        this.selected = selected;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
