package ila.fr.codisintervention.models;

import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.Intervention;

/**
 * Created by marzin on 28/03/18.
 */

public class BigModel {
    private InitializeApplication messageInitialize;
    private Intervention currentIntervention;

    public BigModel() {
    }

    public InitializeApplication getMessageInitialize() {
        return messageInitialize;
    }

    public void setMessageInitialize(InitializeApplication messageInitialize) {
        this.messageInitialize = messageInitialize;
    }

    public Intervention getCurrentIntervention() {
        return currentIntervention;
    }

    public void setCurrentIntervention(Intervention currentIntervention) {
        this.currentIntervention = currentIntervention;
    }

}
