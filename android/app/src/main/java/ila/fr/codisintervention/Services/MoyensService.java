package ila.fr.codisintervention.services;

import java.util.ArrayList;

import ila.fr.codisintervention.entities.Vehicle;

/**
 * Created by aminesoumiaa on 23/03/18.
 */

public class MoyensService {

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
    }


}
