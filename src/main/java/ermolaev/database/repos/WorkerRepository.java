package ermolaev.database.repos;

import ermolaev.models.abstractions.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    @Query(nativeQuery = true, value = "SELECT id FROM workers WHERE workername = :name AND email = :email")
    Optional<Integer> findIdByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT * FROM workers WHERE workername = :name")
    Optional<List<Worker>> findAllWorkersByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM workers WHERE dtype = :position")
    Optional<List<Worker>> findAllWorkersByPosition(@Param("position") String position);
}
