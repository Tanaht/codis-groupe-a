package fr.istic.sit.codisgroupea;

import java.io.IOException;
import java.net.UnknownHostException;

import fr.istic.sit.codisgroupea.socket.SocketForDroneCommunication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CodisGroupeAApplication {

	private static final Logger logger = LoggerFactory.getLogger(CodisGroupeAApplication.class);
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		logger.trace("grosse trace comme ta mère");
		logger.info("grosse info comme ta mère");
		logger.debug("gros debug comme ton père");
		logger.warn("gros warning comme ton boule");
		logger.error("grosse error comme ta naissance");
		logger.error("by Beaulieu ;)");


		ConfigurableApplicationContext context = SpringApplication.run(CodisGroupeAApplication.class, args);
		SocketForDroneCommunication socket = new SocketForDroneCommunication(context);
	}
}
