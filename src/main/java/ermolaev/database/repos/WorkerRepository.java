package ermolaev.database.repos;

import ermolaev.models.abstractions.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Integer> {
}
