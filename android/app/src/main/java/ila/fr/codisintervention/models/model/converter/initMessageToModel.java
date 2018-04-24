package ila.fr.codisintervention.models.model.converter;

import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.model.ApplicationModel;

/**
 * Created by marzin on 24/04/18.
 */

public class initMessageToModel {

    public ApplicationModel getFromMessageInitialize (ApplicationModel app, InitializeApplication init){

        return app;
    }


}
