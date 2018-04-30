package fr.istic.sit.codisgroupea.socket;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonForDroneCommunicationToolBox {
	
	/**
	 * Construct json using mission information
	 * 
	 * @param mission define the type of mission and the path of the drone
	 * @return mission information in json format (defined in the CI)
	 */
	public static String getJsonFromMissionOrder(MissionOrder mission) {
		//Create new json object
		JSONObject jsonMessage = new JSONObject();
		try {
			//type
			jsonMessage.put(DroneServerConstants.TYPE, mission.getType());
			
			//datas
			JSONObject datas = new JSONObject();
			datas.put(DroneServerConstants.TYPE, mission.getMissionType());
			datas.put(DroneServerConstants.INTERVENTION_ID, mission.getInterventionId());
			datas.put(DroneServerConstants.ALTITUDE, 30);
			//Array of positions
			JSONArray locations = new JSONArray();
			for(Location location : mission.getMission()) {
				JSONObject loc = new JSONObject();
				loc.put(DroneServerConstants.LAT, location.getLat());
				loc.put(DroneServerConstants.LNG, location.getLng());
				locations.put(loc);
			}
			datas.put(DroneServerConstants.LOCATIONS, locations);
			jsonMessage.put(DroneServerConstants.DATAS, datas);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonMessage.toString();
	}
	
	/**
	 * 
	 * Get the type of a message got from the drone
	 * 
	 * @param message The message given by the drone
	 * @return The type of the message
	 */
	public static String getMessageType(String message) {
		String type = "";
		try {
			JSONObject jsonObject = new JSONObject(message);
			type = jsonObject.getString(DroneServerConstants.TYPE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return type;
	}
	
	/**
	 * Create the .png file corresponding to the base64 image given by the drone
	 * 
	 * @param message The message given by the drone
	 * @throws IOException
	 */
	public static Photo getPhotoFromMessage(String message) throws IOException {
		Photo photo = new Photo();
		try {
			JSONObject jsonObject = new JSONObject(message);
			
			//Get datas from json
			JSONObject datas = jsonObject.getJSONObject(DroneServerConstants.DATAS);
			//The photo format is String in the message
			photo.setDate(datas.getLong(DroneServerConstants.DATE));
			photo.setInterventionId(datas.getInt(DroneServerConstants.INTERVENTION_ID));
			photo.setPointId(datas.getInt(DroneServerConstants.POINT_ID));
			//Get location of the photo
			JSONObject loc = datas.getJSONObject(DroneServerConstants.LOCATION);
			photo.setLocation(new Location(
					loc.getDouble(DroneServerConstants.LAT), 
					loc.getDouble(DroneServerConstants.LNG)));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//Recover the base64 image from String
		byte[] valueDecoded = Base64.decodeBase64(photo.getPhoto().getBytes("UTF-8"));
		//Put byte array image in BufferedImage
		InputStream in = new ByteArrayInputStream(valueDecoded);
		BufferedImage bImage = ImageIO.read(in);
		String imagePath = DroneServerConstants.IMAGE_LOCATION + DroneServerConstants.IMAGE_NAME + "_" + String.valueOf(photo.getPointId()) + "_" + String.valueOf(photo.getDate()) + "." + DroneServerConstants.IMAGE_EXTENSION;
		ImageIO.write(bImage, DroneServerConstants.IMAGE_EXTENSION, new File(imagePath));
		photo.setPhoto(imagePath);
		return photo;
	}
	
	/**
	 * Return the Location object containing the current position of the drone using the message given by the drone
	 * 
	 * @param message The message given by the drone
	 * @return The current position of the drone 
	 * @throws IOException
	 */
	public static Location getLocationFromMessage(String message) throws IOException {
		Location location = new Location();
		try {
			JSONObject jsonObject = new JSONObject(message);
			//Get datas from json
			JSONObject datas = jsonObject.getJSONObject(DroneServerConstants.DATAS);
			//altitude
			location.setAlt(datas.getDouble(DroneServerConstants.ALTITUDE));
			//location
			JSONObject loc = datas.getJSONObject(DroneServerConstants.LOCATION);
			location.setLat(loc.getDouble(DroneServerConstants.LAT));
			location.setLng(loc.getDouble(DroneServerConstants.LNG));
			location.setInterventionId(datas.getInt(DroneServerConstants.INTERVENTION_ID));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return location;
	}
}
