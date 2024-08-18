package ru.andruf.task_tracker.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andruf.task_tracker.store.entities.TaskStateEntity;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {
}
