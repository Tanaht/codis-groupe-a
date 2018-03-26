package ila.fr.codisintervention.Services;

import java.util.ArrayList;

import ila.fr.codisintervention.Entities.Intervention;
import ila.fr.codisintervention.Entities.Moyen;

/**
 * Created by aminesoumiaa on 23/03/18.
 */

public class InterventionService {

    public ArrayList<Intervention> getInterventionList(){
        ArrayList<Intervention> interventionList = new ArrayList<Intervention>();
        interventionList.add(new Intervention("Secours à personne (SAP)","13h15 12/04/2018","3 Rue Gembetta Rennes"));
        interventionList.add(new Intervention("Secours à personne (SAP)","8h30 12/04/2018","Place de la république Rennes"));
        interventionList.add(new Intervention("Incendie (INC)","11h30 12/04/2018","122 avenue des buttes des coesmes Rennes"));
        interventionList.add(new Intervention("Incendie (INC)","11h30 12/04/2018","12 rue du chêne germain cesson-sévigné"));
        interventionList.add(new Intervention("Secours à personne (SAP)","11h30 12/04/2018","143 Avenue charles tillon Rennes"));
        return interventionList;
    }
}
