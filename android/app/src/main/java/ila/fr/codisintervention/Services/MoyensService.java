package ila.fr.codisintervention.Services;

import java.util.ArrayList;

import ila.fr.codisintervention.Entities.Moyen;

/**
 * Created by aminesoumiaa on 23/03/18.
 */

public class MoyensService {

    public ArrayList<Moyen> getMoyensDispo(){
        ArrayList<Moyen> MoyenDispoList = new ArrayList<Moyen>();
        MoyenDispoList.add(new Moyen("VLCG-13XP201","VLCG",false));
        MoyenDispoList.add(new Moyen("VLCG-13XP103","VLCG",true));
        MoyenDispoList.add(new Moyen("VLCG-13XP205","VLCG",false));
        MoyenDispoList.add(new Moyen("VSAV-12VS32","VSAV",true));
        MoyenDispoList.add(new Moyen("VSAV-12VS20","VSAV",true));
        MoyenDispoList.add(new Moyen("FPT-F21","FPT",false));
        MoyenDispoList.add(new Moyen("FPT-F14","FPT",false));
        return MoyenDispoList;
    }
}
