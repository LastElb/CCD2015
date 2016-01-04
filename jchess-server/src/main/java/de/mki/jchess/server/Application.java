package de.mki.jchess.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the spring boot application
 */
@SpringBootApplication
public class Application {

    /**
     * Default constructor
     */
    public Application() {
        // This constructor needs to be visible for the component scan spring boot does.
    }

    /**
     * Main method to start the spring application.
     * @param args    Application arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
