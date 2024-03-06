package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;
import jakarta.persistence.Entity;

@Entity
public class FrontendDeveloper extends Worker {
    @Override
    public void doJob() {
        System.out.println("Frontend developer " + getName() + " is working...");
    }
}
