package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;
import org.springframework.stereotype.Component;

@Component
public class BackendDeveloper extends Worker {
    public BackendDeveloper(String name, String email) {
        super(name, email);
        setPosition("Backend developer");
    }

    @Override
    public void doJob() {
        System.out.println("Backend developer " + getName() + " is working...");
    }
}
