package org.example.hexlet.dto.users;

import org.example.hexlet.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPage {
    public User user;
}
