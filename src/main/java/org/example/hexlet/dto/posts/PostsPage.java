package org.example.hexlet.dto.posts;

import java.util.List;

import org.example.hexlet.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostsPage {
    private List<Post> posts;
}
