package ila.fr.codisintervention.services;

import java.util.ArrayList;

import ila.fr.codisintervention.entities.Vehicle;
import ila.fr.codisintervention.models.messages.Intervention;

/**
 * Created by aminesoumiaa on 23/03/18.
 * FIXME: to remove
 * It's a Stub to show date on the interface.
 */

public class InterventionService {

    public ArrayList<Intervention> getInterventionList(){
        ArrayList<Intervention> interventionList = new ArrayList<Intervention>();
        Intervention intervention1 = new Intervention();
        Intervention intervention2 = new Intervention();
        Intervention intervention3 = new Intervention();
        Intervention intervention4 = new Intervention();
        Intervention intervention5 = new Intervention();

        intervention1.setId(1);
        intervention2.setId(2);
        intervention3.setId(3);
        intervention4.setId(4);
        intervention5.setId(5);

        intervention1.setCode("Secours à personne (SAP)");
        intervention2.setCode("Secours à personne (SAP)");
        intervention3.setCode("Incendie (INC)");
        intervention4.setCode("Incendie (INC)");
        intervention5.setCode("Secours à personne (SAP)");

        intervention1.setDate(1522160263);
        intervention2.setDate(1522217410);
        intervention3.setDate(1523340610);
        intervention4.setDate(1523340610);
        intervention5.setDate(1522246663);

        intervention1.setAddress("3 Rue Gembetta Rennes");
        intervention2.setAddress("Place de la république Rennes");
        intervention3.setAddress("122 avenue des buttes des coesmes Rennes");
        intervention4.setAddress("12 rue du chêne germain cesson-sévigné");
        intervention5.setAddress("143 Avenue charles tillon Rennes");

        interventionList.add(intervention1);
        interventionList.add(intervention2);
        interventionList.add(intervention3);
        interventionList.add(intervention4);
        interventionList.add(intervention5);

        return interventionList;
        // if empty return new  ArrayList<Intervention>();
    }

    public ArrayList<String> getCodesSinistre() {
        ArrayList<String> codesSinistre = new ArrayList<String>();
        codesSinistre.add("SAP");
        codesSinistre.add("INC");
        return codesSinistre;
    }

    public ArrayList<Vehicle> getMoyensDispo(){
        ArrayList<Vehicle> vehicleAvailablesList = new ArrayList<Vehicle>();
        vehicleAvailablesList.add(new Vehicle("VLCG-13XP201","VLCG",false));
        vehicleAvailablesList.add(new Vehicle("VLCG-13XP103","VLCG",false));
        vehicleAvailablesList.add(new Vehicle("VLCG-13XP205","VLCG",false));
        vehicleAvailablesList.add(new Vehicle("VSAV-12VS32","VSAV",false));
        vehicleAvailablesList.add(new Vehicle("VSAV-12VS20","VSAV",false));
        vehicleAvailablesList.add(new Vehicle("FPT-F21","FPT",false));
        vehicleAvailablesList.add(new Vehicle("FPT-F14","FPT",false));
        return vehicleAvailablesList;
        // if empty return new  ArrayList<Vehicle>();
    }
}
