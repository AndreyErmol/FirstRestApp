package ermolaev.service;

import ermolaev.database.repos.WorkerRepository;
import ermolaev.exceptions.InvalidIdValue;
import ermolaev.exceptions.InvalidPositionValue;
import ermolaev.exceptions.NoWorkerFound;
import ermolaev.models.abstractions.Worker;
import ermolaev.models.impl.BackendDeveloper;
import ermolaev.models.impl.DataScientist;
import ermolaev.models.impl.FrontendDeveloper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WorkerServiceTest {
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
    void findWorkerById_workerExists_shouldReturnWorker() {
        initializeElements();
        int workerId = 1;

        Worker worker = workerService.findWorkerById(workerId);

        assertThat(worker).isNotNull();
        assertEquals(worker.getName(), "Andrew");
        assertEquals(worker.getEmail(), "fylhtdrf@yandex.ru");
    }

    @Test
    void findWorkerById_workerDoesNotExist_shouldThrowException() {
        int workerId = 10000;
        assertThrows(NoWorkerFound.class, () -> workerService.findWorkerById(workerId));
    }

    @Test
    void findWorkerById_invalidId_shouldThrowInvalidIdValue() {
        int workerId = 0;
        assertThrows(InvalidIdValue.class, () -> workerService.findWorkerById(workerId));
    }

    @Test
    void getAllWorkers_shouldReturnAllWorkersList() {
        initializeElements();
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")));

        List<Worker> workerList = workerService.getAllWorkers();

        assertThat(workerList).isNotNull();
        assertThat(workerList).isEqualTo(shouldGet);
    }

    @Test
    void deleteWorkerById_workerExists_shouldDeleteWorker() {
        initializeElements();
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new DataScientist("Andrew", "secondAndrew@email.com"),
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
        initializeElements();
        int workerId = 1000;
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        ));

        workerService.deleteWorkerById(workerId);

        List<Worker> workerList = workerService.getAllWorkers();
        assertThat(workerList).isNotNull();
        assertEquals(workerList, shouldGet);
    }

    @Test
    void addWorker() {
        Worker workerToAdd = new BackendDeveloper("Andrew", "andrew@email.com");

        workerService.addWorker(workerToAdd);

        List<Worker> workerList = workerService.getAllWorkers();
        assertThat(workerList).isNotNull();
        assertThat(workerList).hasSize(1);
        assertEquals(workerList.get(0), workerToAdd);
    }

    @Test
    void deleteWorkerByNameAndEmail_workerExists_shouldDeleteWorker() {
        initializeElements();
        String name = "Alex";
        String email = "alex@email.com";

        workerService.deleteWorkerByNameAndEmail(name, email);

        List<Worker> workerList = workerService.getAllWorkers();
        assertThat(workerList).isNotNull();
        assertThat(workerList).hasSize(3);
        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        )));
    }

    @Test
    void deleteWorkerByNameAndEmail_workerDoesNotExists_shouldThrowException() {
        initializeElements();
        String name = "Test";
        String email = "test@email.com";

        assertThrows(NoWorkerFound.class, () -> workerService.deleteWorkerByNameAndEmail(name, email));

        dataHasNotBeenChangedTest();
    }

    @Test
    void findWorkerId_workerExists_shouldReturnWorkerId() {
        initializeElements();
        String name = "Alex";
        String email = "alex@email.com";

        int id = workerService.findWorkerId(name, email);

        assertEquals(id, 3);
    }

    @Test
    void findWorkerId_workerExists_shouldThrowException() {
        String name = "test";
        String email = "test";

        assertThrows(NoWorkerFound.class, () -> workerService.findWorkerId(name, email));
    }

    @Test
    void findAllWorkersByName_workersExist_shouldReturnListOfWorkers() {
        initializeElements();
        String name = "Andrew";

        List<Worker> workerList = workerService.findAllWorkersByName(name);

        assertThat(workerList).isNotNull();
        assertThat(workerList).hasSize(2);
        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com")
        )));
    }

    @Test
    void findAllWorkersByName_workersDoesNotExist_shouldThrowException() {
        initializeElements();
        String name = "Test";

        assertThrows(NoWorkerFound.class, () -> workerService.findAllWorkersByName(name));
        dataHasNotBeenChangedTest();
    }

    @Test
    void findAllWorkersByPosition_workersExist_shouldReturnListOfWorkers() {
        initializeElements();
        String position = "FrontendDeveloper";

        List<Worker> workerList = workerService.findAllWorkersByPosition(position);

        assertThat(workerList).isNotNull();
        assertThat(workerList).hasSize(2);
        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        )));
    }

    @Test
    void findAllWorkersByPosition_invalidPosition_shouldThrowException() {
        initializeElements();
        String position = "test";

        assertThrows(InvalidPositionValue.class, () -> workerService.findAllWorkersByPosition(position));

        dataHasNotBeenChangedTest();
    }

    @Test
    void findAllWorkersByPosition_noWorkersByPosition_shouldThrowException() {
        testEntityManager.persistAndFlush(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Alex", "alex@email.com"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Michael", "michael@yandex.ru"));
        String position = "DataScientist";

        assertThrows(NoWorkerFound.class, () -> workerService.findAllWorkersByPosition(position));

        List<Worker> workerList = workerService.getAllWorkers();
        assertThat(workerList).isNotNull();
        assertThat(workerList).hasSize(3);
        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        )));
    }

    void dataHasNotBeenChangedTest() {
        List<Worker> workerList = workerService.getAllWorkers();
        assertThat(workerList).isNotNull();
        assertThat(workerList).hasSize(4);
        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        )));
    }

    void initializeElements() {
        testEntityManager.persistAndFlush(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"));
        testEntityManager.persistAndFlush(new DataScientist("Andrew", "secondAndrew@email.com"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Alex", "alex@email.com"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Michael", "michael@yandex.ru"));
    }
}