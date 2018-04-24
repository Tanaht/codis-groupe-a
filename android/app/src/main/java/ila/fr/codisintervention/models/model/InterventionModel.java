package ila.fr.codisintervention.models.model;


import java.util.List;

import ila.fr.codisintervention.models.messages.Photo;

/**
 * Representation of an intervention.
 */
public class InterventionModel {

    /** The id of the intervention */
    private Integer id;

    /** The date of the intervention */
    private long date;

    /** Instance of {@link Position} objet for the intervention */
    private Position position;

    /** Address of the intervention */
    private String address;

    private String sinisterCode;
    private boolean opened;

    private List<Photo> listPhoto;

}
