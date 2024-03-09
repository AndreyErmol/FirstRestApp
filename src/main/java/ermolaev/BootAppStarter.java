package ermolaev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.LogManager;

@SpringBootApplication
public class BootAppStarter {
    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                    BootAppStarter.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(BootAppStarter.class, args);
    }
}
