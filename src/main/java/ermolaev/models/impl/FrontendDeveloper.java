package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;
import jakarta.persistence.Entity;

@Entity
public class FrontendDeveloper extends Worker {
    public FrontendDeveloper(String name, String email) {
        super(name, email);
    }

    public FrontendDeveloper() {
    }

    @Override
    public void doJob() {
        System.out.println("Frontend developer " + getName() + " is working...");
    }
}
