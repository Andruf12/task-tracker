package ru.andruf.task_tracker.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andruf.task_tracker.store.entities.ProjectEntity;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByName(String name);

    Stream<ProjectEntity> streamAllBy();

    Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String prefixName);

}
