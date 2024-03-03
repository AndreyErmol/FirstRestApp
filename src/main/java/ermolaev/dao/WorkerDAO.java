package ermolaev.dao;

import ermolaev.models.abstractions.Worker;
import java.net.http.HttpClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkerDAO {
    public <T extends Worker> ResponseEntity<?> addWorker(T worker) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
