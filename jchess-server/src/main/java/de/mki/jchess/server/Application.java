package de.mki.jchess.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public Application() {
        // This constructor needs to be visible for the component scan spring boot does.
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
