package ila.fr.codisintervention.models;

import android.util.Log;

import ila.fr.codisintervention.models.messages.InitializeApplication;
import ila.fr.codisintervention.models.messages.User;

/**
 * Created by marzin on 28/03/18.
 * The BigModel is work is to contain the Model of the Interventions and Vehicles Requests
 * and to keep the integrity of the datas contained
 * <p>
 * FIXME: To refactor, for now the big model is just an ugly patchwork of severall Class used to represent JSON WebSocket Message
 */
public class BigModel {
    private static final String TAG = "BigModel";

    /**
     * FIXME: Why the BigModel rely on the InitializeApplication message ?
     */
    private InitializeApplication messageInitialize;

    /**
     * FIXME: Why an Intervention currently selected has to be a different class of an intervention
     */
    private InterventionChosen currentIntervention;

    /**
     * Constructor
     */
    public BigModel() {
        // NoOp
    }

    /**
     * Gets message initialize.
     *
     * @return the message initialize
     */
    public InitializeApplication getMessageInitialize() {
        return messageInitialize;
    }

    /**
     * Sets message initialize.
     *
     * @param messageInitialize the message initialize
     */
    public void setMessageInitialize(InitializeApplication messageInitialize) {
        this.messageInitialize = messageInitialize;
    }

    /**
     * Gets current intervention.
     *
     * @return the current intervention
     */
    public InterventionChosen getCurrentIntervention() {
        return currentIntervention;
    }

    /**
     * Sets current intervention.
     *
     * @param currentIntervention the current intervention
     */
    public void setCurrentIntervention(InterventionChosen currentIntervention) {
        this.currentIntervention = currentIntervention;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        if(messageInitialize == null)
            Log.d(TAG, "messageInitialize is null");
        else Log.d(TAG, "messageInitialize.user: " + messageInitialize.getUser());
        return messageInitialize.getUser();
    }

}
