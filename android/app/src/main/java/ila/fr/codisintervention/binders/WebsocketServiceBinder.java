package ila.fr.codisintervention.binders;

import android.os.Binder;

import java.util.List;

import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.messages.Symbol;

/**
 * Created by tanaky on 27/03/18.
 * This class is a binder for the {@link ila.fr.codisintervention.services.websocket.WebsocketService}
 * it allow other android components like activities to have access to the {@link ila.fr.codisintervention.services.websocket.WebsocketService} instance
 */

public class WebSocketServiceBinder extends Binder {

    /**
     * Instance of WebSocketService class, it define an interface WebSocketServiceBinder.IMyServiceMethod to allow request through a predefined API.
     */
    private IMyServiceMethod service;
    //on recoit l'instance du service
    public WebSocketServiceBinder(IMyServiceMethod service) {
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

        /**
         * This method is used to update symbols on a specific intervention
         * @param interventionId
         * @param symbols
         */
        void updateSymbols(int interventionId, List<Symbol> symbols);

        /**
         * This method is used to create a symbol on a specific intervention
         * @param interventionId
         * @param symbols
         */
        void createSymbols(int interventionId, List<Symbol> symbols);

        /**
         * This method is used to delete a symbol on a specific intervention
         * @param interventionId
         * @param symbols
         */
        void deleteSymbols(int interventionId, List<Symbol> symbols);
    }


}
