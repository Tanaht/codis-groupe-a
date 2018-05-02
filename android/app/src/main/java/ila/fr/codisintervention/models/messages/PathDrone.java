package ila.fr.codisintervention.models.messages;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

import ila.fr.codisintervention.models.DronePoint;
import ila.fr.codisintervention.models.Location;
import lombok.Getter;
import lombok.Setter;

/**
 * The message that represent the Path followed by the drone.
 * He contains a list of {@link Location}
 */
@Getter
@Setter
public class PathDrone implements Parcelable {
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
    private List<Location> points;

    /**
     * Instantiates a new Path drone.
     * @param type      the type of the drone path
     * @param path the list of the drone coordinates
     */
    public PathDrone(String type, List<Location> path) {
        this.type = type;
        this.points = path;
        this.altitude = 30;
    }

    /**
     * Instantiates a new PathDrone.
     *
     * @param in the parcel that contain the details of this class
     */
    protected PathDrone(Parcel in) {
        type = in.readString();
        altitude = in.readDouble();
        points = in.createTypedArrayList(Location.CREATOR);
    }


    public static final Creator<PathDrone> CREATOR = new Creator<PathDrone>() {
        @Override
        public PathDrone createFromParcel(Parcel in) {
            return new PathDrone(in);
        }

        @Override
        public PathDrone[] newArray(int size) {
            return new PathDrone[size];
        }
    };

    @Override
    public int describeContents() { return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeDouble(altitude);
        dest.writeTypedList(this.points);
    }
}
