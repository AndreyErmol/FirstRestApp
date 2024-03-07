package ermolaev.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ermolaev.database.repos.WorkerRepository;
import ermolaev.models.abstractions.Worker;
import ermolaev.models.impl.BackendDeveloper;
import ermolaev.service.WorkerService;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.BeforeAll;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@DataJpaTest
public class DbControllerTestIT {
    private MockMvc mockMvc;
    @Mock
    private WorkerService workerService;
    private ObjectMapper objectMapper;

    @Autowired
    public DbControllerTestIT(MockMvc mockMvc, WorkerService workerService) {
        this.mockMvc = mockMvc;
        this.workerService = workerService;
    }

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void addNewWorker_WorkerIsInvalid_ReturnsValidResponseEntity() throws Exception {
        Worker worker = new BackendDeveloper("WorkerFromTest", "workerfromtest@gmail.com");
        String workerJson = objectMapper.writeValueAsString(worker);
        System.out.println(workerJson);

        mockMvc.perform(post("/db/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(workerJson)).andExpect(status().isCreated());
        verify(workerService, times(1)).addWorker(worker);
    }
}
