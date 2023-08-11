package org.example.hexlet.dto;

import io.javalin.validation.ValidationException;

public record NewUserPage(String name, String email, ValidationException e) {
}
