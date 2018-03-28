package fr.istic.sit.codisgroupea.model.message.intervention;

/**
 * The type Position.
 */
public class Position {
    /**
     * The Lat.
     */
    public float lat;
    /**
     * The Lng.
     */
    public float lng;

    /**
     * Instantiates a new Position.
     *
     * @param lat the lat
     * @param lng the lng
     */
    public Position(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public Position(){

    }

    /**
     * Instantiates a new Position.
     *
     * @param position the position
     */
    public Position(fr.istic.sit.codisgroupea.model.entity.Position position) {
        this.lat = (float) position.getLatitude();
        this.lng = (float) position.getLongitude();
    }

    /**
     * Converts the position to a position entity.
     *
     * @return the position
     */
    public fr.istic.sit.codisgroupea.model.entity.Position toPositionEntity() {
        return new fr.istic.sit.codisgroupea.model.entity.Position(lat, lng);
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
