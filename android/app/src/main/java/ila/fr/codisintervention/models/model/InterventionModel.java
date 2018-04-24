package ila.fr.codisintervention.models.model;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ila.fr.codisintervention.models.messages.Intervention;
import ila.fr.codisintervention.models.model.map_icon.drone.PathDrone;
import ila.fr.codisintervention.models.model.map_icon.symbol.Symbol;
import lombok.Setter;
import lombok.Getter;

/**
 * Representation of an intervention.
 */
@Getter
@Setter
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

    private List<Photo> photos;
    private List<Symbol> symbols;
    private List<Unit> units;
    private List<PathDrone> pathDrones;

    public InterventionModel (Intervention intervention){
        this.setAddress(intervention.getAddress());
        this.setDate(intervention.getDate());
        this.setPosition(new Position(intervention.getLocation().getLat(), intervention.getLocation().getLng()));
        this.setSinisterCode(intervention.getCode());
        this.setOpened(false);
        this.setPhotos(setListPhotoFromMessage(intervention));
        this.setPathDrones(null);
        this.setSymbols(null);
        this.setId(intervention.getId());
    }

public List<Photo> setListPhotoFromMessage (Intervention intervention){
        photos = new ArrayList<>();
        for(ila.fr.codisintervention.models.messages.Photo photo : intervention.getPhotos()){
            Photo photoModel = new Photo();
            photoModel.setUri(photo.getUrl());
            photoModel.setDate(new Timestamp(photo.getDate()));
            photoModel.setCoordinates(new Position(photo.getLocation().getLat(), photo.getLocation().getLng()));
            photos.add(photoModel);
        }
        return photos;
    }


}
