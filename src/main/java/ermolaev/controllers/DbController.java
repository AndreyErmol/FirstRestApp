package ermolaev.controllers;

import ermolaev.dao.WorkerDAO;
import ermolaev.models.abstractions.Worker;
import ermolaev.models.impl.BackendDeveloper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("db/")
public class DbController {
    @Autowired
    private WorkerDAO workerDAO;

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public <T extends Worker> void first(@RequestBody T worker) {
        System.out.println(worker.getName());
        System.out.println(worker.getEmail());
        System.out.println(worker.getClass());
        worker.doJob();
        workerDAO.addWorker(worker);
    }
}
