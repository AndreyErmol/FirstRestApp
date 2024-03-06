package ermolaev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class BootAppStarter {
    public static void main(String[] args) {
        SpringApplication.run(BootAppStarter.class, args);
    }
}
