package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;

public class DataScientist extends Worker {
    public DataScientist(String name, String email) {
        super(name, email);
        setPosition("Data scientist");
    }

    @Override
    public void doJob() {
        System.out.println("Data scientist " + getName() + " is working...");
    }
}
