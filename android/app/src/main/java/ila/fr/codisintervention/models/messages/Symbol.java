package ila.fr.codisintervention.models.messages;

import com.google.gson.annotations.Expose;

import ila.fr.codisintervention.models.Location;

/**
 * Created by tanaky on 28/03/18.
 */

public class Symbol {

//    {
//        id: int,
//        shape: String,
//                color: String,
//            location: {lat: float, lng: float},
//			/* La payload n'est utile que pour certain symbole (les points d'eau peuvent ou non avoir un identifiant) */
//			?payload: {
//        identifier: String,
//                details: String
//    }
//    },

    @Expose
    private Integer id;

    @Expose
    private String shape;

    @Expose
    private String color;

    @Expose
    private Location location;

    @Expose
    private Payload payload;


}
