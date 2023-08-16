package org.example.hexlet.dto;

import java.util.List;

import org.example.hexlet.model.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoursesPage {
    public List<Course> courses;
    public String term;
}
