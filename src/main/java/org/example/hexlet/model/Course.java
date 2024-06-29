package org.example.hexlet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public final class Course {
    private Long id;

    @ToString.Include
    private String name;
    private String description;
    private Instant createdAt;

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
