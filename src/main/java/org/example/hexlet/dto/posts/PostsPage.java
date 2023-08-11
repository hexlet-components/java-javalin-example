package org.example.hexlet.dto.posts;

import java.util.List;

import org.example.hexlet.model.Post;

public record PostsPage(List<Post> posts) {
}
