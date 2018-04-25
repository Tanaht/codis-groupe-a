package fr.istic.sit.codisgroupea.socket;

public class DroneServerConstants {

	/**
	 * Types of message exchanged between the server and the drone
	 *
	 */
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
	
	/**
	 * Types of missions
	 *
	 */
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
	
	/**
	 * JSON values
	 */
	public static final String TYPE = "type";
	public static final String INTERVENTION_ID = "interventionId";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	public static final String DATAS = "datas";
	public static final String LOCATION = "location";
	public static final String LOCATIONS = "locations";
	public static final String ALTITUDE = "altitude";
	public static final String PHOTO = "photo";
	public static final String DATE = "date";
	
	/**
	 * File informations
	 */
	public static final String IMAGE_LOCATION = "./src/main/resources/imagesDrone/";
	public static final String IMAGE_NAME = "photo";
	public static final String IMAGE_EXTENSION = "png";
	
	/**
	 * Socket port 8081
	 */
	public static final int SOCKET_PORT = 8081;
}
