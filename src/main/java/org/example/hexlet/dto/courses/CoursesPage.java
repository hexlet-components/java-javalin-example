package org.example.hexlet.dto.courses;

import java.util.List;

import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoursesPage extends BasePage {
    private List<Course> courses;
    private String term;
}
