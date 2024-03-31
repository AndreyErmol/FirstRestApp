package ermolaev.controllers;

import ermolaev.models.abstractions.Worker;
import ermolaev.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/db/")
public class DbController {
    private final WorkerService workerService;

    @Autowired
    public DbController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("worker/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Worker addNewWorker(@RequestBody Worker worker) {
        return workerService.save(worker);
    }

    @GetMapping("worker/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Worker findWorkerById(@PathVariable("id") int id) {
        return workerService.find(id);
    }

    @GetMapping("worker")
    @ResponseStatus(HttpStatus.OK)
    public Worker findWorkerByEmail(@RequestParam("email") String email) {
        return workerService.findByEmail(email);
    }

    @GetMapping("workers")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Worker> findAllWorkers() {
        return workerService.findAll();
    }

    @DeleteMapping("worker/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Worker deleteWorkerById(@PathVariable("id") int id) {
        return workerService.delete(id);
    }

    @DeleteMapping("worker")
    @ResponseStatus(HttpStatus.FOUND)
    public int deleteWorkerByEmail(@RequestParam("email") String email) {
        return workerService.delete(email);
    }

    @GetMapping("worker/find/id")
    @ResponseStatus(HttpStatus.FOUND)
    public int findWorkerId(@RequestParam("email") String email) {
        return workerService.findId(email);
    }

    @GetMapping("workers/find/by/name/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Worker> findAllWorkersByName(@PathVariable("name") String name) {
        return workerService.findAllByName(name);
    }

    @GetMapping("workers/find/by/position/{position}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Worker> findAllWorkersByPosition(@PathVariable("position") String position) {
        return workerService.findAllByPosition(position);
    }
}
