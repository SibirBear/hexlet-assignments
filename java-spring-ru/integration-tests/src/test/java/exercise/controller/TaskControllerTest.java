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

    @BeforeEach
    public void prepareRepo() {
        taskRepository.deleteAll();
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks")).andExpect(status().isOk()).andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    //     * Создание новой задачи
    @Test
    public void testCreateTask() throws Exception {
        var body = new HashMap<>();
        var title = faker.lorem().word();
        var description = faker.lorem().paragraph();

        body.put("title", title);
        body.put("description", description);

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(body));

        mockMvc.perform(request).andExpect(status().isCreated());

        var task = taskRepository.findByTitle(title).get();
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.getCreatedAt()).isNotNull();
        assertThat(task.getUpdatedAt()).isNotNull();

    }

    //     * Просмотр конкретной задачи
    @Test
    public void testGetTask() throws Exception {
        var title = faker.book().title();
        var description = faker.book().publisher();

        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> title)
                .supply(Select.field(Task::getDescription), () -> description)
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .create();
        taskRepository.save(task);

        var request = get("/tasks/" + task.getId());

        var result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        task = taskRepository.findByTitle(title).get();

        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.getCreatedAt()).isNotNull();
        assertThat(task.getUpdatedAt()).isNotNull();


    }

//     * Обновление существующей задачи
    @Test
    public void testUpdateTask() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.book().title())
                .supply(Select.field(Task::getDescription), () -> faker.book().publisher())
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .create();
        taskRepository.save(task);

        var body = new HashMap<>();
        var title = faker.lorem().word();
        var description = faker.lorem().paragraph();

        body.put("title", title);
        body.put("description", description);
        var request = put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(body));

        mockMvc.perform(request).andExpect(status().isOk());

        task = taskRepository.findByTitle(title).get();
        assertThat(task.getTitle()).isEqualTo(title);

    }
//     * Удаление задачи
    @Test
    public void testDeleteTask() throws Exception {
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.book().title())
                .supply(Select.field(Task::getDescription), () -> faker.book().publisher())
                .ignore(Select.field(Task::getCreatedAt))
                .ignore(Select.field(Task::getUpdatedAt))
                .create();
        taskRepository.save(task);

        mockMvc.perform(delete("/tasks/" + task.getId())).andExpect(status().isOk());

        assertThat(taskRepository.findAll()).isEmpty();

    }

}
// END
