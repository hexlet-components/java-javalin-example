package org.example.hexlet.repository;

import java.util.ArrayList;
import java.util.List;

import org.example.hexlet.model.Course;

public class CourseRepository {
    private static List<Course> entities = new ArrayList<Course>();

    public static void save(Course course) {
        course.setId((long) entities.size() + 1);
        entities.add(course);
    }

    public static List<Course> search(String term) {
        var courses = entities.stream()
                .filter(entity -> entity.getName().contains(term))
                .toList();
        return courses;
    }

    public static Course find(Long id) {
        var course = entities.stream()
                .filter(entity -> entity.getId() == id)
                .findAny()
                .orElse(null);
        return course;
    }

    public static List<Course> getEntities() {
        return entities;
    }
}

