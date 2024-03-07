package ermolaev.database.repos;

import ermolaev.models.abstractions.Worker;
import ermolaev.models.impl.BackendDeveloper;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WorkerRepositoryTest {
    @Autowired
    private WorkerRepository workerRepository;
    private Worker worker;

    @BeforeEach
    void setup() {
        worker = new BackendDeveloper("JpaTestName", "JpaTestName@mail.com");
    }

    @Test
    void addNewWorker_validWorkerData_shouldReturnWorker() {
        // given

        // when
        Worker savedWorker = workerRepository.save(worker);
        // then
        assertThat(savedWorker).isNotNull();
        assertThat(savedWorker.getName()).isNotNull();
        assertThat(savedWorker.getEmail()).isNotNull();
    }
}