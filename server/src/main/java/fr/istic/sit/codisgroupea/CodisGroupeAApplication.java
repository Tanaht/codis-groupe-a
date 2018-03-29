package fr.istic.sit.codisgroupea;

import java.io.IOException;
import java.net.UnknownHostException;

import fr.istic.sit.codisgroupea.controller.InterventionSocketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import fr.istic.sit.codisgroupea.socket.SocketForDroneCommunication;

@SpringBootApplication
public class CodisGroupeAApplication {

	private static final Logger logger = LoggerFactory.getLogger(CodisGroupeAApplication.class);

	public static void main(String[] args) throws UnknownHostException, IOException {
		logger.trace("grosse trace comme ta mère");
		logger.info("grosse info comme ta mère");
		logger.debug("gros debug comme ton père");
		logger.warn("gros warning comme ton boule");
		logger.error("grosse error comme ta naissance");


		ConfigurableApplicationContext context = SpringApplication.run(CodisGroupeAApplication.class, args);
		SocketForDroneCommunication socket = new SocketForDroneCommunication(context);
	}
}
