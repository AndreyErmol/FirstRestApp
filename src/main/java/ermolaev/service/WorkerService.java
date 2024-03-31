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

    @Transactional
    public Worker save(Worker worker) {
        logger.debug("Method 'save' started working with the parameter worker: '{}'", worker);

        if (worker == null || worker.getName() == null || worker.getEmail() == null) {
            logger.info("Invalid request body, can't initialize '{}'", worker);
            throw new InvalidRequestBody("Invalid request body, can't initialize " + worker);
        }

        logger.info("Method 'save' is calling method 'save' of {}", WorkerRepository.class.getName());
        return workerRepository.save(worker);
    }

    public Worker find(int id) {
        logger.debug("Method 'find' started working with the parameter id: '{}'", id);

        if (id <= 0) {
            logger.info("ID value should be greater than 0");
            throw new InvalidIdValue("ID value should be greater than 0");
        }

        logger.info("Method 'find' is calling method 'findById' of worker repository.");
        Optional<Worker> worker = workerRepository.findById(id);

        if (worker.isPresent()) {
            return worker.get();
        }

        logger.info("No worker found by id '{}'", id);
        throw new NoWorkerFound("No worker found by id " + id);
    }

    public List<Worker> findAll() {
        logger.info("Method 'findAll' is calling method 'findAll' of {}", WorkerRepository.class.getName());
        return workerRepository.findAll();
    }

    @Transactional
    public Worker delete(int id) {
        logger.debug("Method 'delete' started working with the parameter id: '{}'", id);
        logger.info("Method 'delete' is calling method 'deleteById' of {}",
                WorkerRepository.class.getName());

        Optional<Worker> workerToDelete = workerRepository.findById(id);

        if (workerToDelete.isEmpty()) {
            throw new NoWorkerFound("No worker found by id " + id);
        }

        workerRepository.deleteById(id);
        return workerToDelete.get();
    }

    @Transactional
    public int delete(String email) {
        logger.debug("Method 'delete' started working with the " +
                        "parameter email: '{}'", email);
        logger.info("Method 'delete' is calling method 'deleteByEmail' of {}",
                WorkerRepository.class.getName());

        Optional<Integer> workerToDeleteId = workerRepository.findIdByEmail(email);

        if (workerToDeleteId.isEmpty()) {
            throw new NoWorkerFound("No worker found with email " + email);
        }

        workerRepository.deleteById(workerToDeleteId.get());
        return workerToDeleteId.get();
    }

    public int findId(String email) {
        logger.debug("Method 'findId' started working with the parameter email: '{}'", email);
        logger.info("Method 'findId' is calling method 'findIdByEmail' of {}",
                WorkerRepository.class.getName());

        Optional<Integer> ans = workerRepository.findIdByEmail(email);

        if (ans.isPresent()) {
            return ans.get();
        }

        logger.info("Couldn't find an employee with email '{}'", email);
        throw new NoWorkerFound("Couldn't find an employee with email " + email);
    }

    public List<Worker> findAllWithName(String name) {
        logger.debug("Method 'findAllWithName' started working with the parameter name: '{}'", name);
        logger.info("Method 'findAllWithName' is calling method 'findAllWithName' of {}",
                WorkerRepository.class.getName());

        Optional<List<Worker>> workerList = workerRepository.findAllByName(name);

        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            return workerList.get();
        }

        logger.info("No worker found with name '{}'", name);
        throw new NoWorkerFound("No worker found with name " + name);
    }

    public List<Worker> findAllWithPosition(String position) {
        logger.debug("Method 'findAllWithPosition' started working with the parameter position: '{}'", position);

        if (!position.equalsIgnoreCase("BackendDeveloper") &&
            !position.equalsIgnoreCase("FrontendDeveloper") &&
            !position.equalsIgnoreCase("DataScientist")) {
            logger.info("There is no position named '{}'", position);
            throw new InvalidPositionValue("There is no position with name " + position);
        }

        logger.info("Method 'findAllWithPosition' is calling method 'findAllWithPosition' " +
                "of {}", WorkerRepository.class.getName());

        Optional<List<Worker>> workerList = workerRepository.findAllByPosition(position);

        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            return workerList.get();
        }

        logger.info("No worker found with position '{}'", position);
        throw new NoWorkerFound("No worker found with position " + position);
    }
}
