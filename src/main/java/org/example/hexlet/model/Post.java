package org.example.hexlet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public final class Post {

    private Long id;

    @ToString.Include
    private String title;

    private String body;
    private LocalDateTime createdAt;

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
