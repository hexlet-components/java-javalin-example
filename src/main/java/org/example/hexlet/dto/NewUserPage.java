package org.example.hexlet.dto;

import java.util.List;
import java.util.Map;

import io.javalin.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NewUserPage {
    public String name;
    public String email;
    public Map<String, List<ValidationError<Object>>> errors;
}
