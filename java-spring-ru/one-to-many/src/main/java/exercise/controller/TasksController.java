package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskMapper taskMapper;

//* *GET /tasks* – просмотр списка всех задач
    @GetMapping(path ="")
    public List<TaskDTO> listTasks() {
        var tasks = taskRepository.findAll();
        return taskMapper.map(tasks);
    }

//* *GET /tasks/{id}* – просмотр конкретной задачи
    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found."));
        var dto = taskMapper.map(task);
        return dto;
    }

//* *POST /tasks* – создание новой задачи
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO body) {
        var task = taskMapper.map(body);
        var user = userRepository.findById(body.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        task.setAssignee(user);
        taskRepository.save(task);
        var dto = taskMapper.map(task);
        return dto;
    }

//* *PUT /tasks/{id}* – редактирование задачи. При редактировании м должны иметь возможность поменять название, описание задачи и ответственного разработчика
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO update(@Valid @RequestBody TaskUpdateDTO body, @PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found."));
        var user = userRepository.findById(body.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        taskMapper.update(body, task);
        task.setAssignee(user);
        taskRepository.save(task);
        var dto = taskMapper.map(task);
        return dto;
    }
//* *DELETE /tasks* – удаление задачи
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
    // END
}
