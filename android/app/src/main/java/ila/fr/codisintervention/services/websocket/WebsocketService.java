package ila.fr.codisintervention.services.websocket;

/**
 * Service used to request websocket api
 * Created by tanaky on 26/03/18.
 */

public class WebsocketService {

    private static WebsocketService service;

    public static WebsocketService get() {
        if(service == null)
            service = new WebsocketService();

        return service;
    }

    private WebsocketService() {

    }

}
