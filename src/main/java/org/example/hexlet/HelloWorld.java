package org.example.hexlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.example.hexlet.controller.PostsController;
import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.dto.MainPage;
import org.example.hexlet.dto.UsersPage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;
import org.example.hexlet.util.NamedRoutes;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.javalin.Javalin;
import io.javalin.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorld {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();

        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        // System.setProperty("h2.traceLevel", "TRACE_LEVEL_SYSTEM_OUT=4");

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");
        hikariConfig.setUsername("sa");
        hikariConfig.setPassword("");

        var dataSource = new HikariDataSource(hikariConfig);

        var url = HelloWorld.class.getClassLoader().getResource("init.sql");
        var file = new File(url.getFile());
        var sql = Files.lines(file.toPath())
                .collect(Collectors.joining(" "));

        // log.info(sql);
        try (var connection = dataSource.getConnection();
                var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });
        app.attribute("dataSource", dataSource);

        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
        });

        app.get("/sessions/build", SessionsController::build);
        app.post("/sessions", SessionsController::create);
        app.delete("/sessions", SessionsController::destroy);

        app.get("/posts", PostsController::index);
        app.get("/posts/{id}", PostsController::show);
        app.get("/posts/build", PostsController::build);
        app.post("/posts", PostsController::create);
        app.get("/posts/{id}/edit", PostsController::edit);
        app.patch("/posts/{id}", PostsController::update);
        app.delete("/posts", PostsController::destroy);

        app.get("/", ctx -> {
            var visited = Boolean.valueOf(ctx.cookie("visited"));
            var page = new MainPage(visited, ctx.sessionAttribute("currentUser"));
            ctx.render("index.jte", Collections.singletonMap("page", page));
            ctx.cookie("visited", String.valueOf(true));
        });

        app.get(NamedRoutes.buildUserPath(), ctx -> {
            var page = new BuildUserPage();
            ctx.render("users/build.jte", Collections.singletonMap("page", page));
        });

        app.get(NamedRoutes.userPath("{id}"), ctx -> {
            ctx.render("users/show.jte");
        });

        app.post(NamedRoutes.usersPath(), ctx -> {
            var name = ctx.formParam("name").trim();
            var email = ctx.formParam("email").trim().toLowerCase();

            try {
                var passwordConfirmation = ctx.formParam("passwordConfirmation");
                var password = ctx.formParamAsClass("password", String.class)
                        .check(value -> value.equals(passwordConfirmation), "Passwords are not the same")
                        .check(value -> value.length() > 6, "Password is to short")
                        .get();
                var user = new User(name, email, password);
                UserRepository.save(user);
                ctx.redirect(NamedRoutes.usersPath());
            } catch (ValidationException e) {
                var page = new BuildUserPage(name, email, e.getErrors());
                ctx.render("users/build.jte", Collections.singletonMap("page", page));
            }
        });

        app.get(NamedRoutes.usersPath(), ctx -> {
            var users = new String[] { "ivan", "peter" };
            var page = new UsersPage(users);
            // Отдаем обратно url + query params
            ctx.render("users/index.jte", Collections.singletonMap("page", page));
        });

        app.get(NamedRoutes.buildCoursePath(), ctx -> {
            ctx.render("courses/build.jte");
        });

        app.get(NamedRoutes.coursesPath(), ctx -> {
            var term = ctx.queryParam("term");
            // ctx.sessionAttribute("key", "value");
            List<Course> courses;
            if (term != null) {
                // Фильтруем курсы в соответствии со значением term
                courses = CourseRepository.search(term);
            } else {
                courses = CourseRepository.getEntities();
            }
            var page = new CoursesPage(courses, term);
            page.setFlash(ctx.consumeSessionAttribute("flash"));

            ctx.render("courses/index.jte", Collections.singletonMap("page", page));
        });

        app.post(NamedRoutes.coursesPath(), ctx -> {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            var course = new Course(name, description);
            CourseRepository.save(course);
            ctx.sessionAttribute("flash", "Course has been created!");
            ctx.redirect(NamedRoutes.coursesPath());
        });
        return app;
    }
}
