package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@NoArgsConstructor
public class DataScientist extends Worker {
    private static final Logger logger = LoggerFactory.getLogger(DataScientist.class);

    public DataScientist(String name, String email) {
        super(name, email);
    }

    @Override
    public void doJob() {
        logger.info("Data scientist {} is working...", getName());
    }
}
