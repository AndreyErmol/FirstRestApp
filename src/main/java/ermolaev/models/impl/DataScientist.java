package ermolaev.models.impl;

import ermolaev.models.abstractions.Worker;
import jakarta.persistence.Entity;

@Entity
public class DataScientist extends Worker {
    public DataScientist(String name, String email) {
        super(name, email);
    }

    public DataScientist() {
    }

    @Override
    public void doJob() {
        System.out.println("Data scientist " + getName() + " is working...");
    }
}
