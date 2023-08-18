package org.example.hexlet.dto.posts;

import org.example.hexlet.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostPage {
    private Post post;
}
