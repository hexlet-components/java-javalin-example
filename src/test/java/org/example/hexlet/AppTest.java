package org.example.hexlet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

public class AppTest {

    Javalin app = HelloWorld.getApp();

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Hexlet Javalin Example");
        });
    }

    @Test
    public void testBuildSessionPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/sessions/build");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUsersPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/users");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCoursesPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/courses");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testBuildCourse() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/courses/build");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCreateCourse() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "name=coursename&desdcription=coursedescription";
            var response = client.post("/courses", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("coursename");
        });
    }

    @Test
    public void testPostsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/posts");
            assertThat(response.code()).isEqualTo(200);
        });
    }
}