package ermolaev.database.repos;

import ermolaev.models.abstractions.Worker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Integer> {
    @Query(nativeQuery = true, value = "SELECT id FROM workers WHERE email = :email")
    Optional<Integer> findIdByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT * FROM workers WHERE dtype = :position")
    Optional<List<Worker>> findAllByPosition(@Param("position") String position);

    Optional<Worker> findByEmail(String email);

    Optional<List<Worker>> findAllByName(String name);

    void deleteByEmail(String email);
}
