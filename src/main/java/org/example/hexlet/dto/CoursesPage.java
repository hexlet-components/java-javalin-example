package org.example.hexlet.dto;

import java.util.List;

import org.example.hexlet.model.Course;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CoursesPage {
    public List<Course> courses;
    public String term;
}
