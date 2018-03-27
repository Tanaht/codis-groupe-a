package ila.fr.codisintervention.models.messages;

import java.util.List;

/**
 * Created by tanaky on 27/03/18.
 */

public class InitializeApplication {

    private User user;
    private List<Code> codes;
    private List<Type> types;
    private List<Vehicle> vehicles;
    private List<Demande> demandes;

    public User getUser() {
        return user;
    }

    public List<Code> getCodes() {
        return codes;
    }

    public List<Type> getTypes() {
        return types;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }
}
