package ila.fr.codisintervention.models.messages;

import com.google.gson.annotations.Expose;

import java.util.List;

import ila.fr.codisintervention.models.DronePoint;
import lombok.Getter;
import lombok.Setter;

/**
 * The message that represent the Path followed by the drone.
 * He contains a list of {@link Location}
 */
@Getter
@Setter
public class PathDrone {
	/**
     *The type of the drone path
	*/
	@Expose
	private String type;

    /**
     * The altitude of th drone, is set to 30 per default
     */
    @Expose
    private double altitude;

    /**
     * List of the gps coordinates representing the path.
     */
    @Expose
    private List<Location> path;

    /**
     * Instantiates a new Path drone.
     * @param type      the type of the drone path
     * @param path the list of the drone coordinates
     */
    public PathDrone(String type, List<Location> path) {
        this.type = type;
        this.path = path;
        this.altitude = 30;
    }


}
