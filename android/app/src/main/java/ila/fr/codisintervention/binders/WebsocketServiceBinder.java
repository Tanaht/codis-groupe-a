package ila.fr.codisintervention.binders;

import android.os.Binder;

import ila.fr.codisintervention.models.messages.Intervention;

/**
 * Created by tanaky on 27/03/18.
 */

public class WebsocketServiceBinder extends Binder {

    private IMyServiceMethod service;
    //on recoit l'instance du service
    public WebsocketServiceBinder(IMyServiceMethod service) {
        super();
        this.service = service;
    }

    /** @return l'instance du service */
    public IMyServiceMethod getService(){
        return service;
    }


    /** les méthodes de cette interface seront accessibles par l'activité */
    public interface IMyServiceMethod {
        void disconnect();
        boolean isConnected();


        /**
         * Connect to websocket using credentials in parameters
         * @param username
         * @param password
         */
        void connect(String username, String password);

        void createIntervention(Intervention intervention);
        void chooseIntervention(int id);
    }


}
