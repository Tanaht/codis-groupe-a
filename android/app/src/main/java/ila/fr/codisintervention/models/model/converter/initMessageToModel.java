package ila.fr.codisintervention.models.model.converter;

import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.models.messages.Code;
import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.User;
import ila.fr.codisintervention.models.model.ApplicationModel;
import ila.fr.codisintervention.models.model.InterventionModel;

/**
 * Created by marzin on 24/04/18.
 */

public class initMessageToModel {

    public ApplicationModel getFromMessageInitialize (InitializeApplication init){
        ApplicationModel app = new ApplicationModel();
        app.setListIntervention(listInterventionFromMessage(init.getInterventions()));
        app.setDroneAvailable(true);
        app.setSinisterCodes(listCodeSinistre(init.getCodes()));
        app.setUser(new ila.fr.codisintervention.models.model.user.User(init.getUser()));
        app.se
        return app;
    }

    public List<InterventionModel> listInterventionFromMessage (List<Intervention> interventionList){
        List<InterventionModel> listInterventionModel = new ArrayList<>();
        for(Intervention intervention : interventionList){
            listInterventionModel.add(new InterventionModel(intervention));
        }
         return listInterventionModel;
    }

    public List<String> listCodeSinistre (List<Code> codeList){
        List<String> codeModelList = new ArrayList<>();
        for(Code code : codeList){
            codeModelList.add(code.getLabel());
        }
        return codeModelList;
    }


}
