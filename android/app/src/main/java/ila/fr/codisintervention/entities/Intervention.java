package ila.fr.codisintervention.entities;

/**
 * Created by aminesoumiaa on 23/03/18.
 */

public class Intervention {
    String codeSinistre = null;
    String date = null;
    String address = null;

    public Intervention(String codeSinistre, String date, String address) {
        super();
        this.codeSinistre = codeSinistre;
        this.date = date;
        this.address = address;
    }

    public String getCodeSinistre() {
        return codeSinistre;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public void setCodeSinistre(String codeSinistre) {
        this.codeSinistre = codeSinistre;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
