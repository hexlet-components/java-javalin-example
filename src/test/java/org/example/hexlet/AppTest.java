package org.example.hexlet;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

public class AppTest {

    Javalin app = HelloWorld.getApp();
    // private final String usersJson = new JavalinJackson().toJsonString(UserController.users);

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
    public void testPostsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/posts");
            assertThat(response.code()).isEqualTo(200);
        });
    }
}
