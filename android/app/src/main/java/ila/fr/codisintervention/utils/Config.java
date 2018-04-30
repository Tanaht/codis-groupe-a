package ila.fr.codisintervention.utils;

import android.util.Log;

/**
 * Class used to store some configurations parameters
 * This is a Singleton
 * Created by tanaky on 26/03/18.
 */
public class Config {

    /**
     * True instance of Config
     */
    private static Config config;

    /**
     * Used to retrieve the instance of Config or to create it
     *
     * @return the instance of Config
     */
    public static Config get() {
        if(config == null)
            config = new Config();

        return config;
    }

    /**
     * Host of remote websocket server
     */
    private String host;

    /**
     * Port of remote websocket server
     */
    private int port;

    /**
     * Uri of remote websocket service
     */
    private String uri;

    /**
     * Constructor (private due to Singleton design pattern use)
     * For now the configuration parameters are initialized in it, if we have time it would be good to store them on a file, I think.
     */
    private Config() {
        host = "192.168.43.226";//"lapommevolante.istic.univ-rennes1.fr";
        port = 8080;
        uri = "stomp";

        Log.d("CONFIG", "Application configuration: Host: " + host + ", Port: " + port + ", Uri: " + uri);
    }

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets uri.
     *
     * @return the uri
     */
    public String getUri() {
        return uri;
    }
}
