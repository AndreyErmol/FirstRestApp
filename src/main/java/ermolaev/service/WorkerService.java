package ermolaev.service;

import ermolaev.database.repos.WorkerRepository;
import ermolaev.exceptions.InvalidIdValue;
import ermolaev.exceptions.InvalidPositionValue;
import ermolaev.exceptions.InvalidRequestBody;
import ermolaev.exceptions.NoWorkerFound;
import ermolaev.models.abstractions.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Transactional
    public Worker addWorker(Worker worker) {
        if (worker == null || worker.getName() == null || worker.getEmail() == null) {
            throw new InvalidRequestBody("Invalid request body, can't initialize " + worker);
        }
        return workerRepository.save(worker);
    }

    @Transactional
    public Worker getWorkerById(int id) {
        if (id <= 0) {
            throw new InvalidIdValue("ID value should be greater than 0");
        }

        Optional<Worker> worker = workerRepository.findById(id);
        if (worker.isPresent()) {
            return worker.get();
        }
        throw new NoWorkerFound("No worker found by id " + id);
    }

    @Transactional
    public List<Worker> getAllWorkers() {
        return (List<Worker>) workerRepository.findAll();
    }

    @Transactional
    public void deleteWorkerById(int id) {
        workerRepository.deleteById(id);
    }

    @Transactional
    public void deleteWorkerByNameAndEmail(String name, String email) {
        int id = getWorkerId(name, email);
        deleteWorkerById(id);
    }

    @Transactional
    public int getWorkerId(String name, String email) {
        Optional<Integer> ans = workerRepository.findIdByNameAndEmail(name, email);
        if (ans.isPresent())
            return ans.get();
        throw new NoWorkerFound("Couldn't find an employee with name " + name + " and email " + email);
    }

    @Transactional
    public List<Worker> getAllWorkersByName(String name) {
        Optional<List<Worker>> workerList = workerRepository.findAllWorkersByName(name);
        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            return workerList.get();
        }
        throw new NoWorkerFound("No worker found with name " + name);
    }

    @Transactional
    public List<Worker> getAllWorkersByPosition(String position) {
        if (!position.equalsIgnoreCase("BackendDeveloper") &&
            !position.equalsIgnoreCase("FrontendDeveloper") &&
            !position.equalsIgnoreCase("DataScientist")) {
            throw new InvalidPositionValue("There is no position with name " + position);
        }

        Optional<List<Worker>> workerList = workerRepository.findAllWorkersByPosition(position);
        if (workerList.isPresent() && !workerList.get().isEmpty()) {
            return workerList.get();
        }
        throw new NoWorkerFound("No worker found with position " + position);
    }
}
