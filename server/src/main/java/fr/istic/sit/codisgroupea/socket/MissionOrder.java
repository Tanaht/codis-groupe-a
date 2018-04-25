package fr.istic.sit.codisgroupea.socket;

import java.util.ArrayList;
import java.util.List;

public class MissionOrder {
	
	private String type;
	private int interventionId;
	private String missionType;
	private List<Location> mission;
	
	public MissionOrder() {
		this.type = DroneServerConstants.MESSAGE_TYPES.ASSIGN_MISSION.getName();
		this.mission = new ArrayList<>();
	}

	public MissionOrder(String type, int interventionId, String missionType, List<Location> mission) {
		this.type = type;
		this.interventionId = interventionId;
		this.missionType = missionType;
		this.setMission(mission);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public int getInterventionId() {
		return interventionId;
	}

	public void setInterventionId(int interventionId) {
		this.interventionId = interventionId;
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
	
	public void addLocation(Location location) {
		this.mission.add(location);
	}
}
