package fr.istic.sit.codisgroupea.socket;

public class Photo {
	
	private String photo;
	private long date;
	private Location location;
	
	public Photo() {
		this.photo = "";
		this.date = 0;
	}
	
	public Photo(String photo, long date, Location location) {
		this.photo = photo;
		this.date = date;
		this.location = location;
	}
	
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
}