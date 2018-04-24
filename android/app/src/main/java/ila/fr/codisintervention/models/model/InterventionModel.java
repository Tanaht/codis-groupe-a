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

    public InterventionModel(Intervention intervention){
        Position position = new Position();
        this.setAddress(intervention.getAddress());
        this.setDate(intervention.getDate());
        position.setLatitude(intervention.getLocation().getLat());
        position.setLongitude(intervention.getLocation().getLng());
        this.setPosition(position);
        this.setSinisterCode(intervention.getCode());
        this.setOpened(true);
        this.setListPhotoFromMessage(intervention.getPhotos());

    }

    private List<Photo> setListPhotoFromMessage (List<ila.fr.codisintervention.models.messages.Photo> photoList){
        List<Photo> photos = new ArrayList<>();
        Photo photoModel = new Photo();
        Position position = new Position();
        for(ila.fr.codisintervention.models.messages.Photo photo : photoList){
            photoModel.setUri(photo.getUrl());
            photoModel.setDate(new Timestamp(photo.getDate()));
            position.setLongitude(photo.getLocation().getLng());
            position.setLatitude(photo.getLocation().getLng());
            photoModel.setCoordinates(position);
            photos.add(photoModel);
            photoModel= new Photo();
            position = new Position();
        }

        return photos;
    }


}
