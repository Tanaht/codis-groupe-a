package fr.istic.sit.codisgroupea.socket;

import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
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
	
	public static String getJsonFromMissionOrder(MissionOrder mission) {
		JSONObject jsonMessage = new JSONObject();
		try {
			jsonMessage.put(DroneServerConstants.TYPE, mission.getType());
			JSONObject datas = new JSONObject();
			datas.put(DroneServerConstants.TYPE, mission.getMissionType());
			datas.put(DroneServerConstants.ALTITUDE, 30);
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
	
	public static String getMessageType(String message) {
		String type = "";
		try {
			System.out.println(message);
			JSONObject jsonObject = new JSONObject(message);
			type = jsonObject.getString(DroneServerConstants.TYPE);
			System.out.println("Message type : " + type);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return type;
	}
	
	public static Photo getPhotoFromMessage(String message) throws IOException {
		Photo photo = new Photo();
		try {
			JSONObject jsonObject = new JSONObject(message);
			JSONObject datas = jsonObject.getJSONObject(DroneServerConstants.DATAS);
			System.out.println("Base 64 : " + datas.getString(DroneServerConstants.PHOTO));
			photo.setPhoto(datas.getString(DroneServerConstants.PHOTO));
			photo.setDate(datas.getLong(DroneServerConstants.DATE));
			photo.setDate(datas.getLong(DroneServerConstants.DATE));
			JSONObject loc = datas.getJSONObject(DroneServerConstants.LOCATION);
			double lat = loc.getDouble(DroneServerConstants.LAT);
			double lng = loc.getDouble(DroneServerConstants.LNG);
			photo.setLocation(new Location(lat, lng));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		byte[] valueDecoded = Base64.decodeBase64(photo.getPhoto().getBytes("UTF-8"));
		System.out.println("decoded");
//		DataBuffer buffer = new DataBufferByte(valueDecoded, valueDecoded.length);
		
		InputStream in = new ByteArrayInputStream(valueDecoded);
		BufferedImage bImage = ImageIO.read(in);
		ImageIO.write(bImage, "png", new File("image"+String.valueOf(photo.getDate())+".png"));
		
		return photo;
	}
	
}
