package org.example.hexlet.dto.users;

import java.util.List;

import org.example.hexlet.model.User;

public record UsersPage(List<User> users) {
}

