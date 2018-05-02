package fr.istic.sit.codisgroupea.socket;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.entity.Intervention;
import fr.istic.sit.codisgroupea.model.entity.Position;
import fr.istic.sit.codisgroupea.model.message.LocationMessage;
import fr.istic.sit.codisgroupea.model.message.PhotoMessage;
import fr.istic.sit.codisgroupea.repository.InterventionRepository;
import fr.istic.sit.codisgroupea.repository.PhotoRepository;
import fr.istic.sit.codisgroupea.repository.PositionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

/**
 * 
 * Communication with the drone using socket
 *
 */
@Service
public class SocketForDroneCommunication {

	/** The logger */
	private static final Logger logger = LogManager.getLogger();

    private SimpMessagingTemplate simpMessagingTemplate;
	private InterventionRepository interventionRepository;
	private PhotoRepository photoRepository;
	private PositionRepository positionRepository;

	@Autowired
	private ApplicationContext appContext;

	private Socket socket;
	private ServerSocket serverSocket;

	private boolean sending = false;

	/**
	 * 
	 * Start the socket, then create 2 Thread to send and receive message
	 *
	 * @throws IOException
	 */
	public SocketForDroneCommunication(SimpMessagingTemplate simpMessagingTemplate, InterventionRepository interventionRepository, PhotoRepository photoRepository, PositionRepository positionRepository) throws IOException {
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.interventionRepository = interventionRepository;
		this.photoRepository = photoRepository;
		this.positionRepository = positionRepository;
		Runnable startSocket = new Runnable() {
			@Override
			public void run() {
				try {
					start();
					//Read message from drone
					receiveMessage();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};
		//Start thread
		Thread startSocketThread = new Thread(startSocket);
		startSocketThread.start();
		logger.info("Socket for drone communication is started");
	}

	/**
	 * Start socekt with drone
	 * @throws IOException
	 */
	public void start() throws IOException {
		serverSocket = new ServerSocket(DroneServerConstants.SOCKET_PORT);
		socket = serverSocket.accept();
	}
	
	/**
	 * Stop socket with drone
	 * @throws IOException
	 */
	public void stop() throws IOException {
        serverSocket.close();
        socket.close();
	}
	
	/**
	 * Receive messages sent by the drone
	 */
	public void receiveMessage() {
		Runnable receiverTask = new Runnable() {
			@Override
			public void run() {
				String receivedMessage = "";
				BufferedReader br;
				try {
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					while(!socket.isClosed()) {
						//No receive during sending
						if(!sending) {
							//Get new message (terminated by '\n')
							receivedMessage = br.readLine();
							if(receivedMessage != null) {
								//Get message type
								String messageType = JsonForDroneCommunicationToolBox.getMessageType(receivedMessage);
								//Get photo
								if(messageType.equals(DroneServerConstants.MESSAGE_TYPES.SEND_PHOTO.getName())) {
									Photo photo = JsonForDroneCommunicationToolBox.getPhotoFromMessage(receivedMessage);
									savePhoto(photo);
									sendDronePhoto(photo);
								}
								//Get current location
								else if(messageType.equals(DroneServerConstants.MESSAGE_TYPES.SEND_SITUATION.getName())) {
									Location loc = JsonForDroneCommunicationToolBox.getLocationFromMessage(receivedMessage);
							        sendDronePosition(loc);
								}
							}
						}
					}
					logger.info("Socket is closed");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		//Start thread
		Thread receiverThread = new Thread(receiverTask);
		receiverThread.start();
	}
	
	/**
	 * Send mission to the drone 
	 * @param mission A MissionOrder containing mission informations
	 */
	public void sendMessage(MissionOrder mission) {
		Runnable sendTask = new Runnable() {
			@Override
			public void run() {
				//Lock reception
				sending = true;
				//Convert mission to json
				String message = JsonForDroneCommunicationToolBox.getJsonFromMissionOrder(mission);
				try {
					//Send message
					OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
				    BufferedWriter writer = new BufferedWriter(out);
				    writer.write(message);
				    writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//Release lock
		        sending = false;
			}
		};
		//Start thread
		Thread sendThread = new Thread(sendTask);
		sendThread.start();
	}

	/**
	 * Send the drone position (received by socket) to android (by websocket)
	 * @param location
	 */
    public void sendDronePosition(Location location) {
        Gson gson = new Gson();
        String toJson = gson.toJson(new LocationMessage(location.getLat(), location.getLng(), location.getAlt()),LocationMessage.class);
        simpMessagingTemplate.convertAndSend(RoutesConfig.SEND_DRONE_POSITION_PART1+location.getInterventionId()+RoutesConfig.SEND_DRONE_POSITION_PART2, toJson);
	}

	/**
	 * Save photo in database
	 * @param photo
	 */
	public void savePhoto(Photo photo){
		String uri = photo.getPhoto();
		Position coordinates = new Position(photo.getLocation().getLat(), photo.getLocation().getLng());
		Timestamp date = new Timestamp(photo.getDate());
		Intervention intervention = interventionRepository.getOne(photo.getInterventionId());
		int point = photo.getPointId();
		Position pos = new Position(coordinates.getLatitude(), coordinates.getLongitude());
		positionRepository.save(pos);
		photoRepository.save(new fr.istic.sit.codisgroupea.model.entity.Photo(uri, pos, date, intervention, point));
	}

	/**
	 * Send the photo information (received by socket) to android (by websocket)
	 * @param photo
	 */
	public void sendDronePhoto(Photo photo) {
		Gson gson = new Gson();
		photo.setPhoto("http://192.168.43.107:8080/" + photo.getPhoto());
		String toJson = gson.toJson(photo, Photo.class);
		simpMessagingTemplate.convertAndSend(RoutesConfig.SEND_DRONE_PHOTO_PART1 + photo.getInterventionId() + RoutesConfig.SEND_DRONE_PHOTO_PART2, toJson);
	}
}
