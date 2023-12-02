package exercise.mapper;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.model.Task;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    // BEGIN
    @Mapping(source = "assignee.id", target = "assigneeId")
    public abstract TaskDTO map(Task model);
    @Mapping(source = "assigneeId", target = "assignee.id")
    public abstract Task map(TaskCreateDTO dto);
    //target -> from model, source -> from dto

    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);
    public abstract List<TaskDTO> map(List<Task> models);
    // END

}
