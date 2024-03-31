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
import static org.junit.jupiter.api.Assertions.*;

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
    void findById_workerExists_shouldReturnWorker() {
        initializeElements();
        int workerId = 1;

        Worker worker = workerService.find(workerId);

        assertThat(worker).isNotNull();
        assertEquals("Andrew", worker.getName());
        assertEquals("fylhtdrf@yandex.ru", worker.getEmail());
    }

    @Test
    void findById_workerDoesNotExist_shouldThrowException() {
        int workerId = 10000;
        assertThrows(NoWorkerFound.class, () -> workerService.find(workerId));
    }

    @Test
    void findById_invalidId_shouldThrowInvalidIdValue() {
        int workerId = 0;
        assertThrows(InvalidIdValue.class, () -> workerService.find(workerId));
    }

    @Test
    void findAll_shouldReturnAllWorkersList() {
        initializeElements();
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")));

        List<Worker> workerList = workerService.findAll();

        assertAll(
                () -> assertThat(workerList).isNotNull(),
                () -> assertThat(workerList).isEqualTo(shouldGet)
        );
    }

    @Test
    void deleteById_workerExists_shouldDeleteWorker() {
        initializeElements();
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new DataScientist("Andrew", "secondAndrew@email.com"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        ));
        int workerId = 1;

        workerService.delete(workerId);

        List<Worker> workerList = workerService.findAll();
        assertThat(workerList).isNotNull();
        assertEquals(workerList, shouldGet);
    }

    @Test
    void deleteById_workerDoesNotExists_shouldNotDeleteAnything() {
        initializeElements();
        int workerId = 1000;
        List<Worker> shouldGet = new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        ));

        assertThrows(NoWorkerFound.class, () -> workerService.delete(workerId));
        List<Worker> workerList = workerService.findAll();
        assertThat(workerList).isNotNull();
        assertEquals(workerList, shouldGet);
    }

    @Test
    void save() {
        Worker workerToAdd = new BackendDeveloper("Andrew", "andrew@email.com");

        workerService.save(workerToAdd);

        List<Worker> workerList = workerService.findAll();

        assertAll(
                () -> assertThat(workerList).isNotNull(),
                () -> assertThat(workerList).hasSize(1)
        );

        assertEquals(workerList.get(0), workerToAdd);
    }

    @Test
    void deleteByEmail_workerExists_shouldDeleteWorker() {
        initializeElements();
        String email = "alex@email.com";

        workerService.delete(email);

        List<Worker> workerList = workerService.findAll();

        assertAll(
                () -> assertThat(workerList).isNotNull(),
                () -> assertThat(workerList).hasSize(3)
        );

        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        )));
    }

    @Test
    void deleteByEmail_workerDoesNotExists_shouldThrowException() {
        initializeElements();
        String email = "test@email.com";

        assertThrows(NoWorkerFound.class, () -> workerService.delete(email));

        dataHasNotBeenChangedTest();
    }

    @Test
    void findId_workerExists_shouldReturnWorkerId() {
        initializeElements();
        String email = "alex@email.com";

        int id = workerService.findId(email);

        assertEquals(3, id);
    }

    @Test
    void findId_workerExists_shouldThrowException() {
        String email = "test";

        assertThrows(NoWorkerFound.class, () -> workerService.findId(email));
    }

    @Test
    void findAllWithName_workersExist_shouldReturnListOfWorkers() {
        initializeElements();
        String name = "Andrew";

        List<Worker> workerList = workerService.findAllByName(name);

        assertAll(
                () -> assertThat(workerList).isNotNull(),
                () -> assertThat(workerList).hasSize(2)
        );

        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new DataScientist("Andrew", "secondAndrew@email.com")
        )));
    }

    @Test
    void findAllWithName_workersDoesNotExist_shouldThrowException() {
        initializeElements();
        String name = "Test";

        assertThrows(NoWorkerFound.class, () -> workerService.findAllByName(name));
        dataHasNotBeenChangedTest();
    }

    @Test
    void findAllWithPosition_workersExist_shouldReturnListOfWorkers() {
        initializeElements();
        String position = "FrontendDeveloper";

        List<Worker> workerList = workerService.findAllByPosition(position);

        assertAll(
                () -> assertThat(workerList).isNotNull(),
                () -> assertThat(workerList).hasSize(2)
        );

        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        )));
    }

    @Test
    void findAllWithPosition_invalidPosition_shouldThrowException() {
        initializeElements();
        String position = "test";

        assertThrows(InvalidPositionValue.class, () -> workerService.findAllByPosition(position));

        dataHasNotBeenChangedTest();
    }

    @Test
    void findAllWithPosition_noWorkersByPosition_shouldThrowException() {
        testEntityManager.persistAndFlush(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Alex", "alex@email.com"));
        testEntityManager.persistAndFlush(new FrontendDeveloper("Michael", "michael@yandex.ru"));
        String position = "DataScientist";

        assertThrows(NoWorkerFound.class, () -> workerService.findAllByPosition(position));

        List<Worker> workerList = workerService.findAll();

        assertAll(
                () -> assertThat(workerList).isNotNull(),
                () -> assertThat(workerList).hasSize(3)
        );

        assertEquals(workerList, new ArrayList<>(Arrays.asList(
                new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"),
                new FrontendDeveloper("Alex", "alex@email.com"),
                new FrontendDeveloper("Michael", "michael@yandex.ru")
        )));
    }

    @Test
    void findByEmail_noWorkerWithEmail_shouldThrowException() {
        initializeElements();
        String email = "noSuchEmail@email.com";

        assertThrows(NoWorkerFound.class, () -> workerService.findByEmail(email));

        dataHasNotBeenChangedTest();
    }

    @Test
    void findByEmail_workerExists_shouldReturnWorker() {
        initializeElements();
        String email = "fylhtdrf@yandex.ru";

        Worker worker = workerService.findByEmail(email);

        assertEquals(new BackendDeveloper("Andrew", "fylhtdrf@yandex.ru"), worker);

        dataHasNotBeenChangedTest();
    }

    void dataHasNotBeenChangedTest() {
        List<Worker> workerList = workerService.findAll();
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