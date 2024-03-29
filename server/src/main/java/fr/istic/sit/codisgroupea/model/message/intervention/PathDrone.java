package fr.istic.sit.codisgroupea.model.message.intervention;

import fr.istic.sit.codisgroupea.model.entity.Path;
import fr.istic.sit.codisgroupea.model.entity.PathType;
import fr.istic.sit.codisgroupea.model.entity.Position;
import fr.istic.sit.codisgroupea.model.message.receive.MissionOrderMessage;
import fr.istic.sit.codisgroupea.model.message.utils.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PathDrone {

    private double altitude;
    private List<Location> points;
    private PathType type;



    public PathDrone(Path path){
        altitude = path.getAltitude();

        points = new ArrayList<>();
        for (Position pos : path.getPoints()){
            points.add(new Location(pos));
        }
        type = path.getType();
    }

    public PathDrone(MissionOrderMessage missionOrder) {
        altitude = missionOrder.getAltitude();

        if (missionOrder.getType() != null){
            type = PathType.valueOf(missionOrder.getType());
        }
    }
}
