package org.example.hexlet.dto.posts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexlet.model.Post;

@AllArgsConstructor
@Getter
public class PostPage {
    private Post post;
}
