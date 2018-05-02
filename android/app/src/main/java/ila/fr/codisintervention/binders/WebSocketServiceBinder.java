package ila.fr.codisintervention.binders;

import android.os.Binder;

import java.util.List;

import ila.fr.codisintervention.models.messages.PathDrone;
import ila.fr.codisintervention.models.model.InterventionModel;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;

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

    /**
     * Instantiates a new WebSocket service binder.
     * @param service the WebSocketService instance
     */
    public WebSocketServiceBinder(IMyServiceMethod service) {
        super();
        this.service = service;
    }

    /** @return the instance of WebSocketService */
    public IMyServiceMethod getService(){
        return service;
    }


    /**
     * this nested interface define all the API of the WebSocketService, and this methods are known from android components thanks to the binder.
     */
    public interface IMyServiceMethod {
        /**
         * FIXME: Not Working correctly due to WebSocketService Stomp Library we used
         * Disconnect application from server
         */
        void disconnect();

        /**
         * FIXME: Not Working correctly due to WebSocketService Stomp Library we used
         * Check if the application is connected to server
         * @return whether or not application is connected
         */
        boolean isConnected();


        /**
         * Connect to websocket using credentials in parameters
         * @param username the username
         * @param password the password
         */
        void connect(String username, String password);

        /**
         * TODO: it would be very good to use an interface for the intervention instance to send because like that, Activity doesn't no what field to hydrate on the intervention object
         * TODO: I think the class Intervention could implements severall Interface one interface for each different messages related to intervention -> ICreateIntervention, IInterventionChosen, ...
         * Send a create intervention request to server
         * @param intervention the intervention to send
         */
        void createIntervention(InterventionModel intervention);

        /**
         * Send to server a request to choose an intervention
         * @param id
         */
        void chooseIntervention(int id);

        /**
         * TODO: for now WebSocketService do not store context of which intervention is being selected, so we send it in parameter
         * This method is used to update symbols on a specific intervention
         * @param interventionId the id of intervention where the symbols came from.
         * @param symbols the symbols to update
         */
        void updateSymbols(int interventionId, List<Symbol> symbols);

        /**
         * This method is used to create a symbol on a specific intervention
         * @param interventionId the id of intervention where the symbols came from.
         * @param symbols the symbols to create
         */
        void createSymbols(int interventionId, List<Symbol> symbols);

        /**
         * This method is used to delete a symbol on a specific intervention
         * @param interventionId the id of intervention where the symbols came from.
         * @param symbols the symbols to delete
         */
        void deleteSymbols(int interventionId, List<Symbol> symbols);


        void createPathDrone(int interventionId, PathDrone path);
    }


}
