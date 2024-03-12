package ermolaev.service;

import ermolaev.database.repos.WorkerRepository;
import ermolaev.exceptions.InvalidIdValue;
import ermolaev.exceptions.InvalidPositionValue;
import ermolaev.exceptions.InvalidRequestBody;
import ermolaev.exceptions.NoWorkerFound;
import ermolaev.models.abstractions.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Worker addWorker(Worker worker) {
        logger.debug("Method 'addWorker' started working with the parameter worker: '{}'", worker);
        if (worker == null || worker.getName() == null || worker.getEmail() == null) {
            logger.warn("Invalid request body, can't initialize '{}'", worker);
            throw new InvalidRequestBody("Invalid request body, can't initialize " + worker);
        }
        logger.info("Method 'addWorker' is calling method 'save' of {}", WorkerRepository.class.getName());
        return workerRepository.save(worker);
    }

    @Transactional
    public Worker findWorkerById(int id) {
        logger.debug("Method 'findWorkerById' started working with the parameter id: '{}'", id);
        if (id <= 0) {
            logger.warn("ID value should be greater than 0");
            throw new InvalidIdValue("ID value should be greater than 0");
        }
        logger.info("Method 'findWorkerById' is calling method 'findById' of worker repository.");
        Optional<Worker> worker = workerRepository.findById(id);
        if (worker.isPresent()) {
            return worker.get();
        }
        logger.warn("No worker found by id '{}'", id);
        throw new NoWorkerFound("No worker found by id " + id);
    }

    @Transactional
    public List<Worker> getAllWorkers() {
        logger.info("Method 'getAllWorkers' is calling method 'findAll' of {}", WorkerRepository.class.getName());
        return workerRepository.findAll();
    }

    @Transactional
    public void deleteWorkerById(int id) {
        logger.debug("Method 'deleteWorkerById' started working with the parameter id: '{}'", id);
        logger.info("Method 'deleteWorkerById' is calling method 'deleteById' of {}",
                WorkerRepository.class.getName());
        workerRepository.deleteById(id);
    }

    @Transactional
    public void deleteWorkerByNameAndEmail(String name, String email) {
        logger.debug("Method 'deleteWorkerByNameAndEmail' started working with the " +
                        "parameters name: '{}', email: '{}'", name, email);
        logger.info("Method 'deleteWorkerByNameAndEmail' is calling method 'findWorkerId' of {}",
                WorkerService.class.getName());
        int id = findWorkerId(name, email);
        logger.info("Method 'deleteWorkerByNameAndEmail' is calling method 'deleteWorkerById' of {}",
                WorkerService.class.getName());
        deleteWorkerById(id);
    }

    @Transactional
    public int findWorkerId(String name, String email) {
        logger.debug("Method 'findWorkerId' started working with the parameters name: '{}', email: '{}'", name, email);
        logger.info("Method 'findWorkerId' is calling method 'findIdByNameAndEmail' of {}",
                WorkerRepository.class.getName());
        Optional<Integer> ans = workerRepository.findIdByNameAndEmail(name, email);
        if (ans.isPresent())
            return ans.get();
        logger.warn("Couldn't find an employee with name '{}' and email '{}'", name, email);
        throw new NoWorkerFound("Couldn't find an employee with name " + name + " and email " + email);
    }

    @Transactional
    public List<Worker> findAllWorkersByName(String name) {
        logger.debug("Method 'findAllWorkersByName' started working with the parameter name: '{}'", name);
        logger.info("Method 'findAllWorkersByName' is calling method 'findAllWorkersByName' of {}",
                WorkerRepository.class.getName());

        Optional<List<Worker>> workerList = workerRepository.findAllWorkersByName(name);
        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            return workerList.get();
        }
        logger.warn("No worker found with name '{}'", name);
        throw new NoWorkerFound("No worker found with name " + name);
    }

    @Transactional
    public List<Worker> findAllWorkersByPosition(String position) {
        logger.debug("Method 'findAllWorkersByPosition' started working with the parameter position: '{}'", position);

        if (!position.equalsIgnoreCase("BackendDeveloper") &&
            !position.equalsIgnoreCase("FrontendDeveloper") &&
            !position.equalsIgnoreCase("DataScientist")) {
            logger.warn("There is no position named '{}'", position);
            throw new InvalidPositionValue("There is no position with name " + position);
        }
        logger.info("Method 'findAllWorkersByPosition' is calling method 'findAllWorkersByPosition' " +
                "of " + WorkerRepository.class.getName());
        Optional<List<Worker>> workerList = workerRepository.findAllWorkersByPosition(position);
        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            return workerList.get();
        }
        logger.warn("No worker found with position '{}'", position);
        throw new NoWorkerFound("No worker found with position " + position);
    }
}
