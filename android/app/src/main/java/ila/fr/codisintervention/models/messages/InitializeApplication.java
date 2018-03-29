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
    private List<Intervention> interventions;

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

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public Intervention getInterventionById (int id){
        for(Intervention intervention : interventions){
            if(intervention.getId() == id){
                return intervention;
            }
        }
        return null;
    }

    public void setInterventionClosedById (int id) {
        //Si valeur id à -1 pas de supresion
        if (id != -1) {
            for (Intervention intervention : interventions) {
                if (intervention.getId() == id) {
                    interventions.remove(intervention);
                }
            }
        }
    }
}
