package org.example.hexlet.repository;

import java.util.ArrayList;
import java.util.List;

import org.example.hexlet.model.User;

public class UserRepository {
    private static List<User> entities = new ArrayList<User>();

    public static void save(User user) {
        user.setId((long) entities.size() + 1);
        entities.add(user);
    }

    public static List<User> search(String term) {
        var users = entities.stream()
                .filter(entity -> entity.getName().startsWith(term))
                .toList();
        return users;
    }

    public static User find(Long id) {
        var user = entities.stream()
                .filter(entity -> entity.getId() == id)
                .findAny()
                .orElse(null);
        return user;
    }

    public static void delete(Long id) {

    }

    public static List<User> getEntities() {
        return entities;
    }
}