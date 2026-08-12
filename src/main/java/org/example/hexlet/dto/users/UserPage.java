package org.example.hexlet.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexlet.model.User;

@AllArgsConstructor
@Getter
public class UserPage {
    public User user;
}
