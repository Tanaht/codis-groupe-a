package fr.istic.sit.codisgroupea;

import java.io.IOException;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import fr.istic.sit.codisgroupea.socket.SocketForDroneCommunication;

@SpringBootApplication
public class CodisGroupeAApplication {

	public static void main(String[] args) throws UnknownHostException, IOException {
		ConfigurableApplicationContext context = SpringApplication.run(CodisGroupeAApplication.class, args);
		SocketForDroneCommunication socket = new SocketForDroneCommunication(context);
	}
}
