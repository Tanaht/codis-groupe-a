package fr.istic.sit.codisgroupea.model.message.receive;

import java.util.List;

/**
 * A mission order message
 * message form :
 *      * {
 *      *   points :: [{
 *      *     lat :: double,
 *      *     lng :: double
 *      *   }],
 *      *   altitude :: double,
 *      *   type :: PathType
 *      * }
 */
public class MissionOrderMessage {

    private double altitude;

    private String type;

    private List<Location> points;

    public static class Location{

        private double lat;

        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Location> getPoints() {
        return points;
    }

    public void setPoints(List<Location> points) {
        this.points = points;
    }
}
