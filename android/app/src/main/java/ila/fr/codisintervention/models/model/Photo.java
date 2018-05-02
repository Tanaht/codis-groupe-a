package ila.fr.codisintervention.models.model;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Photo informations stored in the database.
 */
@Getter
@Setter
public class Photo {

    /** The url of the photo */
    private String uri;

    /** Instance of {@link Position} for the location of the photo */
    private Position coordinates;

    /** Instance of {@link Timestamp} for the date of the photo */
    private Timestamp date;

    private int interventionId;

    private int pointId;

    public Photo(ila.fr.codisintervention.models.messages.Photo photo){
        uri = photo.getUrl();
        coordinates = new Position(photo.getLocation());
        date = new Timestamp(photo.getDate());
        interventionId = photo.getInterventionId();
        pointId = photo.getPointId();
    }

    public Photo(ila.fr.codisintervention.models.PhotoReception photo){
        uri = photo.getPhoto();
        coordinates = new Position(photo.getLocation());
        date = new Timestamp(photo.getDate());
        interventionId = photo.getInterventionId();
        pointId = photo.getPointId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return uri.equals(photo.uri);
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }
}
