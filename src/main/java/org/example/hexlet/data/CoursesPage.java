package org.example.hexlet.data;

import java.util.List;

import org.example.hexlet.domain.Course;

public record CoursesPage(List<Course> courses, String term) {
}
