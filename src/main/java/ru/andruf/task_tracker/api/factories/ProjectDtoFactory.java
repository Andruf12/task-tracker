package ru.andruf.task_tracker.api.factories;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.andruf.task_tracker.api.dto.ProjectDto;
import ru.andruf.task_tracker.store.entities.ProjectEntity;

@Component
public class ProjectDtoFactory {
    public ProjectDto makeProjectDto(ProjectEntity entity){

        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
