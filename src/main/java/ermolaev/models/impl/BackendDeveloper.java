package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Entity
public class BackendDeveloper extends Worker {
    @Override
    public void doJob() {
        System.out.println("Backend developer " + getName() + " is working...");
    }
}
