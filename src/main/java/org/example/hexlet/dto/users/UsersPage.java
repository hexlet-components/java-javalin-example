package org.example.hexlet.dto.users;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexlet.model.User;

@AllArgsConstructor
@Getter
public class UsersPage {
    public List<User> users;
}
