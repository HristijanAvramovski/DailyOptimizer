package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "tasks")
public class Task {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name ="name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name="date_created")
    private LocalDateTime dateCreated;
    @Column(name="priority")
    private String priority;
    @Column(name="proposal", columnDefinition = "TEXT")
    private String proposal;

    public Task() {
    }

    public Task(String name, String description, LocalDateTime dateCreated,String priority, String proposal) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.priority = priority;
        this.proposal = proposal;
    }
}
