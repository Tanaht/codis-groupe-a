package fr.istic.sit.codisgroupea.socket;

public class Photo {
	
	private String photo;
	private long date;
	private Location location;
	private int interventionId;
	private int pointId;

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getInterventionId() {return interventionId;}
	public void setInterventionId(int interventionId) {this.interventionId = interventionId;}
	public int getPointId() {return pointId;}
	public void setPointId(int pointId) {this.pointId = pointId;}
}
