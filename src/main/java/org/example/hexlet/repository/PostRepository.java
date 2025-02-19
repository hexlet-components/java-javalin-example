package org.example.hexlet.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.hexlet.model.Post;

public class PostRepository {
    private static List<Post> entities = new ArrayList<Post>();

    public static void save(Post post) {
        post.setId((long) entities.size() + 1);
        post.setCreatedAt(LocalDateTime.now());
        entities.add(post);
    }

    // public static List<Post> search(String term) {
    //     var posts = entities.stream()
    //             .filter(entity -> entity.getName().startsWith(term))
    //             .toList();
    //     return posts;
    // }

    public static Optional<Post> find(Long id) {
        var post = entities.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
        return post;
    }

    public static List<Post> getEntities() {
        return entities;
    }

    public static void delete(Long id) {
        entities.removeIf(post -> post.getId() == id);
    }

    public static void removeAll() {
        entities = new ArrayList<Post>();
    }
}


