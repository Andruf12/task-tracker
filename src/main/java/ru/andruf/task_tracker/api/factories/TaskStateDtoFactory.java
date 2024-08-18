package ru.andruf.task_tracker.api.factories;

import org.springframework.stereotype.Component;
import ru.andruf.task_tracker.api.dto.TaskStateDto;
import ru.andruf.task_tracker.store.entities.TaskStateEntity;

@Component
public class TaskStateDtoFactory {
    public TaskStateDto makeTaskStateDto(TaskStateEntity entity){

        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ordinal(entity.getOrdinal())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
