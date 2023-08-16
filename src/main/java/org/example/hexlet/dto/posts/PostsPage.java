package org.example.hexlet.dto.posts;

import java.util.List;

import org.example.hexlet.model.Post;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostsPage {
    public List<Post> posts;
}
