package ila.fr.codisintervention.utils;

import android.content.SharedPreferences;

/**
 * Class used to store and retrieve shared preferences
 * Created by tanaky on 26/03/18.
 */

public class Config {
    private static Config config;

    public static Config get() {
        if(config == null)
            config = new Config();

        return config;
    }

    private String host;
    private int port;
    private String uri;

    private Config() {
        host = "";
        port = 8080;
        uri = "/stomp";
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
