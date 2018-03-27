package ila.fr.codisintervention.binders;

import android.os.Binder;

import rx.Subscriber;
import ua.naiksoftware.stomp.LifecycleEvent;

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
         * Connect to websocket using credentials in parameters and return success or failure of connection
         * @param username
         * @param password
         */
        boolean connect(String username, String password);
        /**
         * Connect to websocket using credentials in parameters
         * @param username
         * @param password
         * @param subscriber Subscriber to get notified when the websocket is closed or in error
         */
        boolean connect(String username, String password, Subscriber<LifecycleEvent> subscriber);
    }


}
