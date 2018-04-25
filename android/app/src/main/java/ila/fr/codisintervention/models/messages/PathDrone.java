package ila.fr.codisintervention.models.messages;

import com.google.gson.annotations.Expose;

import java.util.List;

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
    private double alt;

    /**
     * List of the gps coordinates representing the path.
     */
    private List<Location> locations;

    /**
     * Instantiates a new Path drone.
     * @param type      the type of the drone path
     * @param locations the list of the drone coordinates
     */
    public PathDrone(String type, List<Location> locations) {
        this.type = type;
        this.locations = locations;
        this.alt = 30;
    }

}
