package ru.andruf.task_tracker.api.factories;

import org.springframework.stereotype.Component;
import ru.andruf.task_tracker.api.dto.TaskDto;
import ru.andruf.task_tracker.store.entities.TaskEntity;

@Component
public class TaskDtoFactory {
    public TaskDto makeTaskDto(TaskEntity entity){

        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
