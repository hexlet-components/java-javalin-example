package org.example.hexlet.dto;

import java.util.List;

import org.example.hexlet.model.Course;

public record CoursesPage(List<Course> courses, String term) {
}
