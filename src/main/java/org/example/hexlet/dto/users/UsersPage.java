package org.example.hexlet.dto.users;

import java.util.List;

import org.example.hexlet.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsersPage {
    public List<User> users;
}
