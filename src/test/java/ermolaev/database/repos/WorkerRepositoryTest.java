package ermolaev.database.repos;

import ermolaev.exceptions.InvalidIdValue;
import ermolaev.exceptions.NoWorkerFound;
import ermolaev.models.abstractions.Worker;
import ermolaev.models.impl.BackendDeveloper;
import ermolaev.models.impl.FrontendDeveloper;
import ermolaev.service.WorkerService;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WorkerRepositoryTest {
    private WorkerService workerService;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        workerService = new WorkerService(workerRepository);
    }

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(workerRepository).isNotNull();
        assertThat(testEntityManager).isNotNull();
    }

    @Test
    void getWorkerById_workerExists_shouldReturnWorker() {
        int workerId = 1;
        testEntityManager.persistAndFlush(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Alex", "alex@email.com"));

        Worker worker = workerService.getWorkerById(workerId);

        assertThat(worker).isNotNull();
        assertEquals(worker.getName(), "Andrew");
        assertEquals(worker.getEmail(), "fylhtdrf@yandex.ru");
    }

    @Test
    void getWorkerById_workerDoesNotExist_shouldThrowException() {
        int workerId = 10000;
        assertThrows(NoWorkerFound.class, () -> workerService.getWorkerById(workerId));
    }

    @Test
    void getWorkerById_invalidId_shouldThrowInvalidIdValue() {
        int workerId = 0;
        assertThrows(InvalidIdValue.class, () -> workerService.getWorkerById(workerId));
    }

    @Test
    void getAllWorkers_shouldReturnAllWorkersAsList() {
        testEntityManager.persistAndFlush(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Alex", "alex@email.com"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Michael", "michael@yandex.ru"));
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")));

        List<Worker> workerList = workerService.getAllWorkers();

        assertThat(workerList).isNotNull();
        assertThat(workerList).isEqualTo(shouldGet);
    }

    @Test
    void deleteWorkerById_workerExists_shouldDeleteWorker() {
        testEntityManager.persistAndFlush(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Alex", "alex@email.com"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Michael", "michael@yandex.ru"));
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        ));
        int workerId = 1;

        workerService.deleteWorkerById(workerId);

        List<Worker> workerList = workerService.getAllWorkers();
        assertThat(workerList).isNotNull();
        assertEquals(workerList, shouldGet);
    }

    @Test
    void deleteWorkerById_workerDoesNotExists_shouldNotDeleteAnything() {
        testEntityManager.persistAndFlush(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Alex", "alex@email.com"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Michael", "michael@yandex.ru"));
        int workerId = 4;
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        ));

        workerService.deleteWorkerById(workerId);

        List<Worker> workerList = workerService.getAllWorkers();
        assertThat(workerList).isNotNull();
        assertEquals(workerList, shouldGet);
    }
}