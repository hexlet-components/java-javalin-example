package org.example.hexlet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public final class Post {

    private Long id;

    @ToString.Include
    private String title;

    private String body;
    private Instant createdAt;

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
