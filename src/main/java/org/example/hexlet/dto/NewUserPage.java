package org.example.hexlet.dto;

import io.javalin.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NewUserPage {
    public String name;
    public String email;
    public ValidationException e;
}
