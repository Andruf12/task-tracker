package ru.andruf.task_tracker.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    private String description;
    @Builder.Default
    private Instant createdAt = Instant.now();


}
