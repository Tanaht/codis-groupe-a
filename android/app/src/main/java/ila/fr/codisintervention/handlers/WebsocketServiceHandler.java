package ila.fr.codisintervention.handlers;

import android.os.Handler;
import android.util.Log;

import ila.fr.codisintervention.services.websocket.WebsocketService;

/**
 * Created by tanaky on 27/03/18.
 */

public class WebsocketServiceHandler extends Handler {
    private static final String TAG = "WebsocketServiceHandler";

    public WebsocketServiceHandler() {
        Log.d(TAG, "Instanciated");
    }
}
