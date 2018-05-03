package fr.istic.sit.codisgroupea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CodisGroupeAApplication {

	private static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(CodisGroupeAApplication.class, args);

	}

}
