package ermolaev.dao;

import ermolaev.models.abstractions.Worker;
import java.net.http.HttpClient;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class WorkerDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T extends Worker> void save(T worker, String workerPosition) {
        jdbcTemplate.update("insert into workers(workerName, email, position) values(?, ?, ?)",
                worker.getEmail(), worker.getName(), workerPosition);
    }
}
