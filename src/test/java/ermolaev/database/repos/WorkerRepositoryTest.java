package ermolaev.database.repos;

import ermolaev.models.abstractions.Worker;
import ermolaev.models.impl.BackendDeveloper;
import ermolaev.models.impl.FrontendDeveloper;
import ermolaev.service.WorkerService;
import jakarta.persistence.EntityManager;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class WorkerRepositoryTest {
    private WorkerService workerService;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        testEntityManager.persistAndFlush(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Alex", "alex@email.com"));
        workerService = new WorkerService(workerRepository);
    }

    @Test
    void getAllWorkers() {
        List<Worker> workerList = workerService.getAllWorkers();
        System.out.println( "Worker list from test" + workerList);
        assertThat(workerList).isNotNull();

    }
}