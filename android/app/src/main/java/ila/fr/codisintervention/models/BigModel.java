package ila.fr.codisintervention.models;

import android.util.Log;

import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.User;

/**
 * Created by marzin on 28/03/18.
 */

public class BigModel {
    private static final String TAG = "BigModel";
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

    public User getUser() {
        if(messageInitialize == null)
            Log.d(TAG, "messageInitialize is null");
        else Log.d(TAG, "messageInitialize.user: " + messageInitialize.getUser());
        return messageInitialize.getUser();
    }

}