package ermolaev.database.repos;

import ermolaev.models.abstractions.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    @Query(nativeQuery = true, value = "SELECT id FROM workers WHERE workername = :name AND email = :email")
    Optional<Integer> findIdByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
