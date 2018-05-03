package fr.istic.sit.codisgroupea.model.entity;

import fr.istic.sit.codisgroupea.model.message.receive.MissionOrderMessage;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Representation of the drone's path.
 */
@Entity
@Data
public class Path {
    /** The id of the path */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /** The altitude of the path */
    @NotNull
    private double altitude;

    /** List of {@link Position} for all the point in the path */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Position> points;

    /** Instance of {@link PathType} for the type of the path */
    @Enumerated
    @NotNull
    private PathType type;


    /**
     * Instantiates a new Path.
     */
    public Path() {
        this.altitude = 30.0;
    }

    /**
     * Instantiates a new Path.
     *
     * @param altitude the altitude
     * @param points   the points
     * @param type     the type
     */
    public Path(double altitude, List<Position> points, PathType type) {
        this.altitude = altitude;
        this.points = points;
        this.type = type;
    }

    public Path(MissionOrderMessage missionOrder) {
        altitude = missionOrder.getAltitude();

        if (missionOrder.getType() != null){
            type = PathType.valueOf(missionOrder.getType());
        }else{
            type = PathType.SEGMENT;
        }
    }
}
