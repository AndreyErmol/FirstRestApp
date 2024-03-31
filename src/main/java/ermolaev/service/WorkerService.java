package ermolaev.service;

import ermolaev.database.repos.WorkerRepository;
import ermolaev.exceptions.InvalidIdValue;
import ermolaev.exceptions.InvalidPositionValue;
import ermolaev.exceptions.InvalidRequestBody;
import ermolaev.exceptions.NoWorkerFound;
import ermolaev.models.abstractions.Worker;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerService.class);
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public Worker find(int id) {
        logger.debug("Method 'find' started working with the parameter id: {}", id);

        if (id <= 0) {
            logger.info("ID value should be greater than 0");
            throw new InvalidIdValue("ID value should be greater than 0");
        }

        logger.info("Method 'find' is calling method 'findById' of worker repository.");
        Optional<Worker> worker = workerRepository.findById(id);

        if (worker.isPresent()) {
            logger.info("The worker has been found: {}", worker.get());
            return worker.get();
        }

        logger.info("No worker found by id {}", id);
        throw new NoWorkerFound("No worker found by id " + id);
    }

    public List<Worker> findAll() {
        logger.info("Method 'findAll' is calling method 'findAll' of {}", WorkerRepository.class.getName());
        return workerRepository.findAll();
    }

    public int findId(String email) {
        logger.debug("Method 'findId' started working with the parameter email: {}", email);
        logger.info("Method 'findId' is calling method 'findIdByEmail' of {}",
                WorkerRepository.class.getName());

        Optional<Integer> worker = workerRepository.findIdByEmail(email);

        if (worker.isPresent()) {
            logger.info("The worker has been found: {}", worker.get());
            return worker.get();
        }

        logger.info("Couldn't find an employee with email {}", email);
        throw new NoWorkerFound("No worker found with email " + email);
    }

    @Transactional
    public Worker findByEmail(String email) {
        logger.debug("Method findByEmail started working with the parameter email: {}", email);
        logger.info("Method 'findByEmail' is calling method 'findId' of {}",
                                    WorkerService.class.getName());
        int workerId = findId(email);
        logger.info("Return value of 'findId' method is {}", workerId);
        return find(workerId);
    }

    public List<Worker> findAllByName(String name) {
        logger.debug("Method 'findAllByName' started working with the parameter name: {}", name);
        logger.info("Method 'findAllByName' is calling method 'findAllByName' of {}",
                WorkerRepository.class.getName());

        Optional<List<Worker>> workerList = workerRepository.findAllByName(name);

        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            logger.info("Workers have been found: {}", workerList.get());
            return workerList.get();
        }

        logger.info("No workers found with name {}", name);
        throw new NoWorkerFound("No workers found with name " + name);
    }

    public List<Worker> findAllByPosition(String position) {
        logger.debug("Method 'findAllByPosition' started working with the parameter position: {}", position);

        if (!position.equalsIgnoreCase("BackendDeveloper") &&
                !position.equalsIgnoreCase("FrontendDeveloper") &&
                !position.equalsIgnoreCase("DataScientist")) {
            logger.info("There is no position named {}", position);
            throw new InvalidPositionValue("There is no position with name " + position);
        }

        logger.info("Method 'findAllByPosition' is calling method 'findAllByPosition' " +
                "of {}", WorkerRepository.class.getName());

        Optional<List<Worker>> workerList = workerRepository.findAllByPosition(position);

        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            logger.info("Workers have been found: {}", workerList.get());
            return workerList.get();
        }

        logger.info("No workers found with position {}", position);
        throw new NoWorkerFound("No workers found with position " + position);
    }

    @Transactional
    public Worker save(Worker worker) {
        logger.debug("Method 'save' started working with the parameter worker: {}", worker);

        if (worker == null || worker.getName() == null || worker.getEmail() == null) {
            logger.info("Invalid request body, can't initialize {}", worker);
            throw new InvalidRequestBody("Invalid request body, can't initialize " + worker);
        }

        logger.info("Method 'save' is calling method 'save' of {}", WorkerRepository.class.getName());
        return workerRepository.save(worker);
    }

    @Transactional
    public Worker delete(int id) {
        logger.debug("Method 'delete' started working with the parameter id: {}", id);
        logger.info("Method 'delete' is calling method 'findById' of {}",
                WorkerRepository.class.getName());

        Optional<Worker> workerToDelete = workerRepository.findById(id);

        if (workerToDelete.isEmpty()) {
            logger.info("No workers found by id {}", id);
            throw new NoWorkerFound("No worker found by id " + id);
        }

        logger.info("Method 'delete' is calling method 'deleteById' of {}",
                WorkerRepository.class.getName());
        workerRepository.deleteById(id);
        return workerToDelete.get();
    }

    @Transactional
    public int delete(String email) {
        logger.debug("Method 'delete' started working with the " +
                        "parameter email: {}", email);
        logger.info("Method 'delete' is calling method 'findIdByEmail' of {}",
                WorkerRepository.class.getName());

        Optional<Integer> workerToDeleteId = workerRepository.findIdByEmail(email);

        if (workerToDeleteId.isEmpty()) {
            throw new NoWorkerFound("No worker found with email " + email);
        }

        logger.info("Method 'delete' is calling method 'deleteByEmail' of {}",
                        WorkerRepository.class.getName());
        workerRepository.deleteById(workerToDeleteId.get());
        return workerToDeleteId.get();
    }
}
