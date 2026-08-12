package org.example.hexlet.dto.users;

import io.javalin.validation.ValidationError;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuildUserPage {
    public String name;
    public String email;
    public Map<String, List<ValidationError<Object>>> errors;
}
