package fr.istic.sit.codisgroupea.socket;

import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Position;

public class Location {
		
	private double lat;
	private double lng;
	private double alt;

	private int interventionId;
	
	public Location() {
	}

	public Location(Position pos, Intervention interv){
		this.lat = pos.getLatitude();
		this.lng = pos.getLongitude();
		this.alt = 0;
		this.interventionId = interv.getId();
	}

	public Location(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
		this.alt = 0;
	}

	public Location(double lat, double lng, double alt, int interventionId) {
		this.lat = lat;
		this.lng = lng;
		this.alt = alt;
		this.interventionId = interventionId;
	}

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

	public double getAlt() {
		return alt;
	}

	public void setAlt(double alt) {
		this.alt = alt;
	}

	public int getInterventionId() {
		return interventionId;
	}

	public void setInterventionId(int interventionId) {
		this.interventionId = interventionId;
	}
}
