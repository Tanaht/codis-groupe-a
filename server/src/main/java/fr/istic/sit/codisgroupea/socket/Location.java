package fr.istic.sit.codisgroupea.socket;

public class Location {
		
	private double lat;
	private double lng;
	private double alt;
	
	public Location() {
	}
	
	public Location(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
		this.alt = 0;
	}

	public Location(double lat, double lng, double alt) {
		this.lat = lat;
		this.lng = lng;
		this.alt = alt;
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
}
