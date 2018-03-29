package ila.fr.codisintervention.models;

import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;

/**
 * Created by marzin on 28/03/18.
 */

public class BigModel {
    private InitializeApplication messageInitialize;
    private InterventionChosen currentIntervention;

    public BigModel() {
    }

    public InitializeApplication getMessageInitialize() {
        return messageInitialize;
    }

    public void setMessageInitialize(InitializeApplication messageInitialize) {
        this.messageInitialize = messageInitialize;
    }

    public InterventionChosen getCurrentIntervention() {
        return currentIntervention;
    }

    public void setCurrentIntervention(InterventionChosen currentIntervention) {
        this.currentIntervention = currentIntervention;
    }

}
