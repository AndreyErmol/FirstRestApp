package ermolaev.service;

import ermolaev.database.repos.WorkerRepository;
import ermolaev.exceptions.InvalidIdValue;
import ermolaev.exceptions.InvalidPositionValue;
import ermolaev.exceptions.InvalidRequestBody;
import ermolaev.exceptions.NoWorkerFound;
import ermolaev.models.abstractions.Worker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerService.class);
    private final WorkerRepository workerRepository;

    public Worker find(int id) {
        logger.debug(Logs.METHOD_STARTED_WORKING, "find", "id", id);

        if (id <= 0) {
            logger.info("ID value should be greater than 0");
            throw new InvalidIdValue("ID value should be greater than 0");
        }

        logger.debug(Logs.METHOD_CALLING_METHOD, "find", "findById", WorkerRepository.class.getName());
        Optional<Worker> worker = workerRepository.findById(id);

        if (worker.isPresent()) {
            logger.info(Logs.WORKER_FOUND, worker.get());
            return worker.get();
        }

        logger.info("No worker found by id {}", id);
        throw new NoWorkerFound("No worker found by id " + id);
    }

    public List<Worker> findAll() {
        logger.debug(Logs.METHOD_CALLING_METHOD, "findAll", "findAll", WorkerRepository.class.getName());
        return (List<Worker>) workerRepository.findAll();
    }

    public int findId(String email) {
        logger.debug(Logs.METHOD_STARTED_WORKING, "findId", "email", email);
        logger.debug(Logs.METHOD_CALLING_METHOD, "findId", "findIdByEmail", WorkerRepository.class.getName());

        Optional<Integer> worker = workerRepository.findIdByEmail(email);

        if (worker.isPresent()) {
            logger.info(Logs.WORKER_FOUND, worker.get());
            return worker.get();
        }

        logger.info("Couldn't find an employee with email {}", email);
        throw new NoWorkerFound("No worker found with email " + email);
    }

    @Transactional
    public Worker findByEmail(String email) {
        logger.debug(Logs.METHOD_STARTED_WORKING, "findByEmail", "email", email);
        logger.debug(Logs.METHOD_CALLING_METHOD, "findByEmail", "findByEmail", WorkerRepository.class.getName());

        Optional<Worker> foundWorker = workerRepository.findByEmail(email);

        if (foundWorker.isPresent()) {
            logger.info(Logs.WORKER_FOUND, foundWorker.get());
            return foundWorker.get();
        }

        logger.info(Logs.NO_WORKER_FOUND_WITH, "email", email);
        throw new NoWorkerFound("No worker found with email " + email);
    }

    public List<Worker> findAllByName(String name) {
        logger.debug(Logs.METHOD_STARTED_WORKING, "findAllByName", "name", name);
        logger.debug(Logs.METHOD_CALLING_METHOD, "findAllByName", "findAllByName", WorkerRepository.class.getName());

        Optional<List<Worker>> workerList = workerRepository.findAllByName(name);

        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            logger.info("Workers have been found: {}", workerList.get());
            return workerList.get();
        }

        logger.info(Logs.NO_WORKER_FOUND_WITH, "name", name);
        throw new NoWorkerFound("No workers found with name " + name);
    }

    public List<Worker> findAllByPosition(String position) {
        logger.debug(Logs.METHOD_STARTED_WORKING, "findAllByPosition", "position", position);

        if (!position.equalsIgnoreCase("BackendDeveloper") &&
                !position.equalsIgnoreCase("FrontendDeveloper") &&
                !position.equalsIgnoreCase("DataScientist")) {
            logger.info("There is no position named {}", position);
            throw new InvalidPositionValue("There is no position with name " + position);
        }

        logger.debug(Logs.METHOD_CALLING_METHOD, "findAllByPosition", "findAllByPosition",
                WorkerRepository.class.getName());

        Optional<List<Worker>> workerList = workerRepository.findAllByPosition(position);

        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            logger.info("Workers have been found: {}", workerList.get());
            return workerList.get();
        }

        logger.info(Logs.NO_WORKER_FOUND_WITH, "position", position);
        throw new NoWorkerFound("No workers found with position " + position);
    }

    @Transactional
    public Worker save(Worker worker) {
        logger.debug(Logs.METHOD_STARTED_WORKING, "save", "worker", worker);

        if (worker == null || worker.getName() == null || worker.getEmail() == null) {
            logger.info("Invalid request body, can't initialize {}", worker);
            throw new InvalidRequestBody("Invalid request body, can't initialize " + worker);
        }

        logger.debug(Logs.METHOD_CALLING_METHOD, "save", "save", WorkerRepository.class.getName());
        return workerRepository.save(worker);
    }

    @Transactional
    public void delete(int id) {
        logger.debug(Logs.METHOD_STARTED_WORKING, "delete", "id", id);
        logger.debug(Logs.METHOD_CALLING_METHOD, "delete", "deleteById", WorkerRepository.class.getName());
        workerRepository.deleteById(id);
    }

    @Transactional
    public void delete(String email) {
        logger.debug(Logs.METHOD_STARTED_WORKING, "delete", "email", email);
        logger.debug(Logs.METHOD_CALLING_METHOD, "delete", "deleteByEmail", WorkerRepository.class.getName());
        workerRepository.deleteByEmail(email);
    }
}
