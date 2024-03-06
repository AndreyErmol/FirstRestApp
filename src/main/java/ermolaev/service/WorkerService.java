package ermolaev.service;

import ermolaev.database.repos.WorkerRepository;
import ermolaev.models.abstractions.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    @Transactional
    public <T extends Worker> void addWorker(T worker) {
        workerRepository.save(worker);
//        try {
//            dao.save(worker, workerPosition);
//        } catch (DataAccessException e) {
//            throw new FailedToSaveResource("Error occurred while saving the resource: " + worker);
//        }
    }
}
