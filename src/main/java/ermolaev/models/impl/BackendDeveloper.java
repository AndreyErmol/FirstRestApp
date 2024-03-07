package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;
import jakarta.persistence.Entity;

@Entity
public class BackendDeveloper extends Worker {
    public BackendDeveloper(String name, String email) {
        super(name, email);
    }

    public BackendDeveloper() {
    }

    @Override
    public void doJob() {
        System.out.println("Backend developer " + getName() + " is working...");
    }
}
