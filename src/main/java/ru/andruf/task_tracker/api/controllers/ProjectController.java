package ru.andruf.task_tracker.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.andruf.task_tracker.api.dto.AckDto;
import ru.andruf.task_tracker.api.dto.ProjectDto;
import ru.andruf.task_tracker.api.exceptions.BadRequestException;
import ru.andruf.task_tracker.api.exceptions.NotFoundException;
import ru.andruf.task_tracker.api.factories.ProjectDtoFactory;
import ru.andruf.task_tracker.store.entities.ProjectEntity;
import ru.andruf.task_tracker.store.repositories.ProjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class ProjectController {
    ProjectDtoFactory projectDtoFactory;
    ProjectRepository projectRepository;
    public static final String CREATE_PROJECT = "/api/projects";
    public static final String EDIT_PROJECT = "api/projects/{project_id}";
    public static final String FETCH_PROJECT = "api/projects";
    public static final String DELETE_PROJECT = "api/projects/{project_id}";
    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name){

        if (name.trim().isEmpty()){
            throw new BadRequestException("Name can't be empty");
        }


        projectRepository
                .findByName(name)
                .ifPresent(project ->{
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });

        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(name)
                        .build()

        );

        return projectDtoFactory.makeProjectDto(project);
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editProject(@RequestParam String name,
                                @PathVariable("project_id") Long projectId ){
        if (name.trim().isEmpty()){
            throw new BadRequestException("Name can't be empty");
        }

        ProjectEntity project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with \"%s\" doesn't exist.", projectId)));

        projectRepository
                .findByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });

        project.setName(name);

        project = projectRepository.saveAndFlush(project);


        return projectDtoFactory.makeProjectDto(project);
    }

    @GetMapping(FETCH_PROJECT)
    public List<ProjectDto> fetchProject(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);




        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());

    }

    @DeleteMapping(DELETE_PROJECT)
    public AckDto deleteProject(@PathVariable("project_id") Long projectId){

        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with \"%s\" doesn't exist.", projectId)));

        projectRepository.deleteById(projectId);

        return AckDto.makeDefault(true);
    }

}



