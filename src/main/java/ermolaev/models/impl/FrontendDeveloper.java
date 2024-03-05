package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;

public class FrontendDeveloper extends Worker {
    public FrontendDeveloper(String name, String email) {
        super(name, email);
        setPosition("Frontend developer");
    }

    @Override
    public void doJob() {
        System.out.println("Frontend developer " + getName() + " is working...");
    }
}
