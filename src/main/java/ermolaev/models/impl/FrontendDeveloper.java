package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@NoArgsConstructor
public class FrontendDeveloper extends Worker {
    private static final Logger logger = LoggerFactory.getLogger(FrontendDeveloper.class);

    public FrontendDeveloper(String name, String email) {
        super(name, email);
    }

    @Override
    public void doJob() {
        logger.info("Frontend developer {} is working...", getName());
    }
}
