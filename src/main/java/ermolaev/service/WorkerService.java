package ermolaev.service;

import ermolaev.dao.WorkerDAO;
import ermolaev.exceptions.FailedToSaveResource;
import ermolaev.models.abstractions.Worker;
import ermolaev.models.impl.BackendDeveloper;
import ermolaev.models.impl.DataScientist;
import ermolaev.models.impl.FrontendDeveloper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {
    @Autowired
    private WorkerDAO dao;

    public <T extends Worker> void addWorker(T worker) {
        String workerPosition;

        if (worker.getClass().equals(BackendDeveloper.class)) {
            workerPosition = "Backend developer";
        } else if (worker.getClass().equals(FrontendDeveloper.class)) {
            workerPosition = "Frontend developer";
        } else if (worker.getClass().equals(DataScientist.class)) {
            workerPosition = "Data scientist";
        } else {
            workerPosition = "Position isn't defined";
        }

        try {
            dao.save(worker, workerPosition);
        } catch (DataAccessException e) {
            throw new FailedToSaveResource("Error occurred while saving the resource: " + worker);
        }
    }
}
