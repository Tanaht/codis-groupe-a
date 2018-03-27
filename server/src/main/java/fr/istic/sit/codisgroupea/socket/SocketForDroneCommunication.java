package fr.istic.sit.codisgroupea.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * 
 * Communication with the drone using socket
 *
 */
public class SocketForDroneCommunication {
	
	private Socket socket;
	private ServerSocket serverSocket;
	
	private boolean sending = false;
	
	/**
	 * 
	 * The the server socket, then create 2 Thread to send and receive message
	 * 
	 * @param context The application context
	 * @throws IOException
	 */
	public SocketForDroneCommunication(ConfigurableApplicationContext context) throws IOException {
		
		this.start(context);
		
		Runnable receiverTask = new Runnable() {
			
			@Override
			public void run() {
				String receivedMessage = "";
				System.out.println("receiveThread running");
				BufferedReader br;
				try {
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					while(true) {
						if(!sending) {
							receivedMessage = br.readLine();
							if(receivedMessage != null) {
								System.out.println("Received message : " + receivedMessage);
								String messageType = JsonForDroneCommunicationToolBox.getMessageType(receivedMessage);
								System.out.println("Test : "  + messageType + " - " + DroneServerConstants.MESSAGE_TYPES.SEND_PHOTO.getName() + " : " + (messageType.equals(DroneServerConstants.MESSAGE_TYPES.SEND_PHOTO.getName())));
								if(messageType.equals(DroneServerConstants.MESSAGE_TYPES.SEND_PHOTO.getName())) {
									System.out.println("Get photo");
									JsonForDroneCommunicationToolBox.getPhotoFromMessage(receivedMessage);
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		

		Runnable sendTask = new Runnable() {
			@Override
			public void run() {
				sending = true;
				MissionOrder mission = new MissionOrder();
				sendMessage(JsonForDroneCommunicationToolBox.getJsonFromMissionOrder(mission));
		        System.out.println("Message send");
		        sending = false;
			}
		};
		
		Thread sendThread = new Thread(sendTask);
		sendThread.start();
		Thread receiverThread = new Thread(receiverTask);
		receiverThread.start();
		
	}
	
	public void start(ConfigurableApplicationContext context) throws IOException {
		serverSocket = new ServerSocket(8081);
		socket = serverSocket.accept();
	}
	
	public void stop() throws IOException {
        serverSocket.close();
        socket.close();
	}
	
	public void sendMessage(String message) {
		try {
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
		    BufferedWriter writer = new BufferedWriter(out);
		    writer.write(message);
		    writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
