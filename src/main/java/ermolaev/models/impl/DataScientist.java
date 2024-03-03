package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;

public class DataScientist extends Worker {
    @Override
    public void doJob() {
        System.out.println("Data scientist " + getName() + " is working...");
    }
}
