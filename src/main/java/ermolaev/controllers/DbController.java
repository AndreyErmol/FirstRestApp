package ermolaev.controllers;

import ermolaev.dao.WorkerDAO;
import ermolaev.models.abstractions.Worker;
import ermolaev.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("db/")
public class DbController {
    @Autowired
    private WorkerService workerService;

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public <T extends Worker> void first(@RequestBody T worker) {
        workerService.addWorker(worker);
    }
}
