package fr.istic.sit.codisgroupea.socket;

import fr.istic.sit.codisgroupea.controller.DronePositionController;
import org.springframework.context.ConfigurableApplicationContext;

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
public class SocketForDroneCommunication {

    private ConfigurableApplicationContext context;

	private static SocketForDroneCommunication instance;

	private Socket socket;
	private ServerSocket serverSocket;

	private boolean sending = false;

	public static void create(ConfigurableApplicationContext context) throws IOException {
        if(instance == null && context != null){
            instance = new SocketForDroneCommunication(context);
        }
    }

	public static SocketForDroneCommunication get() throws IOException {
	    /*
	    TODO: throw null pointer exception
	     */
		return instance;
	}

	/**
	 * 
	 * The the server socket, then create 2 Thread to send and receive message
	 *
	 * @throws IOException
	 */
	private SocketForDroneCommunication(ConfigurableApplicationContext context) throws IOException {
		this.context = context;

		this.start();
		
		//Read message from drone
		this.receiveMessage();
		
		//Exemple of mission order sending
//		MissionOrder mission = new MissionOrder();
//		mission.setMissionType(DroneServerConstants.MISSION_TYPES.MISSION_CYCLE.getName());
//		mission.addLocation(new Location(48.1148383, -1.6388297));
//		mission.addLocation(new Location(48.1153379, -1.6391757));
//		mission.setInterventionId(1);
//		this.sendMessage(mission);
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
					while(true) {
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
							        DronePositionController dpc = (DronePositionController) context.getBean("dronePositionController");
							        dpc.sendDronePosition(loc);
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
}
