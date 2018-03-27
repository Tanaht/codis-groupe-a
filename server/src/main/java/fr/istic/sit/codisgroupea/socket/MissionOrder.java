package fr.istic.sit.codisgroupea.socket;

import java.util.ArrayList;
import java.util.List;

public class MissionOrder {
	
	private String type;
	private String missionType;
	private List<Location> mission;
	
	public MissionOrder() {
		this.type = DroneServerConstants.MESSAGE_TYPES.ASSIGN_MISSION.getName();
		this.missionType = DroneServerConstants.MISSION_TYPES.MISSION_CYCLE.getName();
		this.mission = new ArrayList<>();
		this.mission.add(new Location(48.1148383, -1.6388297));
		this.mission.add(new Location(48.1153379, -1.6391757));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMissionType() {
		return missionType;
	}

	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}

	public List<Location> getMission() {
		return mission;
	}

	public void setMission(List<Location> mission) {
		this.mission = mission;
	}
}
