package exercise.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

@SpringBootTest
@AutoConfigureMockMvc
// BEGIN
public class TaskControllerTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    ObjectMapper om;

    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();
        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring");
    }
    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks")).andExpect(status().isOk()).andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    private Task generateTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lebowski().actor())
                .supply(Select.field(Task::getDescription), () -> faker.lebowski().quote())
                .create();
    }

    //     * Просмотр конкретной задачи
    @Test
    public void testGetTask() throws Exception {
        var task = generateTask();
        taskRepository.save(task);

        var request = get("/tasks/" + task.getId());
        var result = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn();
        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                v -> v.node("title").isEqualTo(task.getTitle()),
                v -> v.node("description").isEqualTo(task.getDescription())
        );

    }

    //     * Создание новой задачи
    @Test
    public void testCreateTask() throws Exception {
        var task = generateTask();

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(task));

        mockMvc.perform(request).andExpect(status().isCreated());

        var newTask = taskRepository.findByTitle(task.getTitle()).get();
        assertThat(newTask.getTitle()).isEqualTo(task.getTitle());
        assertThat(newTask.getDescription()).isEqualTo(task.getDescription());
        assertThat(newTask).isNotNull();

    }

//     * Обновление существующей задачи
    @Test
    public void testUpdateTask() throws Exception {
        var task = generateTask();
        taskRepository.save(task);

        var body = new HashMap<>();

        body.put("title", "updated title");

        var request = put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(body));

        mockMvc.perform(request).andExpect(status().isOk());

        task = taskRepository.findById(task.getId()).get();
        assertThat(task.getTitle()).isEqualTo(body.get("title"));

    }
//     * Удаление задачи
    @Test
    public void testDeleteTask() throws Exception {
        var task = generateTask();
        taskRepository.save(task);

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isOk());

        task = taskRepository.findById(task.getId()).orElse(null);
        assertThat(task).isNull();

    }

}
// END
