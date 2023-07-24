package org.example.hexlet.data;

import io.javalin.validation.ValidationException;

public record NewUserPage(String name, String email, ValidationException e) {
}
