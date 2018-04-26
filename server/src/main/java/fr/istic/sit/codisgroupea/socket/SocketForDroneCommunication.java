package fr.istic.sit.codisgroupea.socket;

import com.google.gson.Gson;
import fr.istic.sit.codisgroupea.config.RoutesConfig;
import fr.istic.sit.codisgroupea.model.message.LocationMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Communication with the drone using socket
 *
 */
@Service
public class SocketForDroneCommunication {

    private SimpMessagingTemplate simpMessagingTemplate;

	private Socket socket;
	private ServerSocket serverSocket;

	private boolean sending = false;

	/**
	 * 
	 * The the server socket, then create 2 Thread to send and receive message
	 *
	 * @throws IOException
	 */
	public SocketForDroneCommunication(SimpMessagingTemplate simpMessagingTemplate) throws IOException {
        this.simpMessagingTemplate = simpMessagingTemplate;

        this.start();
		
		//Read message from drone
		this.receiveMessage();
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
									JsonForDroneCommunicationToolBox.getPhotoFromMessage(receivedMessage);
								}
								//Get current location
								else if(messageType.equals(DroneServerConstants.MESSAGE_TYPES.SEND_SITUATION.getName())) {
									Location loc = JsonForDroneCommunicationToolBox.getLocationFromMessage(receivedMessage);
							        sendDronePosition(loc);
								}
							}
						}
					}
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

    public void sendDronePosition(Location location) {
        Gson gson = new Gson();
        String toJson = gson.toJson(new LocationMessage(location.getLat(), location.getLng(), location.getAlt()),LocationMessage.class);
        simpMessagingTemplate.convertAndSend(RoutesConfig.SEND_DRONE_POSITION_PART1+location.getInterventionId()+RoutesConfig.SEND_DRONE_POSITION_PART2, toJson);
	}
}
