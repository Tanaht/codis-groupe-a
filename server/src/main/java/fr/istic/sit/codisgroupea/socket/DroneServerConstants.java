package fr.istic.sit.codisgroupea.socket;

public class DroneServerConstants {

	public static enum MESSAGE_TYPES {
		SEND_PHOTO("SEND_PHOTO"),
		SEND_SITUATION("SEND_SITUATION"),
		ASSIGN_MISSION("ASSIGN_MISSION");
		private String name;
		MESSAGE_TYPES(String name){
			this.name= name;
		}
		public String getName() {
			return this.name;
		}
	}
	
	public static enum MISSION_TYPES {
		MISSION_CYCLE("CYCLE"),
		MISSION_GRID("GRID"),
		MISSION_SEGMENT("SEGMENT");
		private String name;
		MISSION_TYPES(String name){
			this.name= name;
		}
		public String getName() {
			return this.name;
		}
	}
	
	public static final String TYPE = "type";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	public static final String DATAS = "datas";
	public static final String LOCATION = "location";
	public static final String LOCATIONS = "locations";
	public static final String ALTITUDE = "altitude";
	public static final String PHOTO = "photo";
	public static final String DATE = "date";
}
