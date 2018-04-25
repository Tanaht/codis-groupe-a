package fr.istic.sit.codisgroupea.socket;

public class Location {
		
	private double lat;
	private double lng;
	private double alt;

	private double interventionId;
	
	public Location() {
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

	public double getInterventionId() {
		return interventionId;
	}

	public void setInterventionId(double interventionId) {
		this.interventionId = interventionId;
	}
}
