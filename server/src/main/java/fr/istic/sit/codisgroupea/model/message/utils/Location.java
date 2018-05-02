package fr.istic.sit.codisgroupea.model.message.utils;

import fr.istic.sit.codisgroupea.constraints.groups.Message;
import fr.istic.sit.codisgroupea.model.entity.Position;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Represent a GPS coordinate
 */
public class Location {

    /** Latitude of the location. */
    @NotNull(groups = {
            Message.CreateUnitMessageReception.class,
            Message.CreateUnitMessageWithSymbolReception.class,
            Message.UnitMessageReception.class
    })
    @Min(value = -90, groups = {
            Message.CreateUnitMessageReception.class,
            Message.CreateUnitMessageWithSymbolReception.class,
            Message.UnitMessageReception.class
    })
    @Max(value = 90, groups = {
            Message.CreateUnitMessageReception.class,
            Message.CreateUnitMessageWithSymbolReception.class,
            Message.UnitMessageReception.class
    })
    private double lat;

    /** Longitude of the location. */
    @NotNull(groups = {
            Message.CreateUnitMessageReception.class,
            Message.CreateUnitMessageWithSymbolReception.class,
            Message.UnitMessageReception.class
    })
    @Min(value = -180, groups = {
            Message.CreateUnitMessageReception.class,
            Message.CreateUnitMessageWithSymbolReception.class,
            Message.UnitMessageReception.class
    })
    @Max(value = 180, groups = {
            Message.CreateUnitMessageReception.class,
            Message.CreateUnitMessageWithSymbolReception.class,
            Message.UnitMessageReception.class
    })
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    /**
     * Empty Constructor
     */
    public Location () {}

    /**
     * @param lat the latitude.
     * @param lng the longitude.
     */
    public Location (double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Location(Position position) {
        this.lat = position.getLatitude();
        this.lng = position.getLongitude();
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
