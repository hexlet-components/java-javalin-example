package org.example.hexlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.example.hexlet.controller.CarsController;
import org.example.hexlet.controller.PostsController;
import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.MainPage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.BaseRepository;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;
import org.example.hexlet.util.NamedRoutes;

@Slf4j
public class HelloWorld {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    private static TemplateEngine createTemplateEngine() {
        var classLoader = HelloWorld.class.getClassLoader();
        var codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = HelloWorld.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();

        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        // System.setProperty("h2.traceLevel", "TRACE_LEVEL_SYSTEM_OUT=4");

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");

        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");

        log.info(sql);
        try (var connection = dataSource.getConnection();
                var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app =
                Javalin.create(
                        config -> {
                            config.bundledPlugins.enableDevLogging();
                            config.fileRenderer(new JavalinJte(createTemplateEngine()));

                            config.routes.before(
                                    ctx -> {
                                        ctx.contentType("text/html; charset=utf-8");
                                    });

                            config.routes.get("/sessions/build", SessionsController::build);
                            config.routes.post("/sessions", SessionsController::create);
                            config.routes.delete("/sessions", SessionsController::destroy);

                            config.routes.get("/posts", PostsController::index);
                            config.routes.get("/posts/{id}", PostsController::show);
                            config.routes.get("/posts/build", PostsController::build);
                            config.routes.post("/posts", PostsController::create);
                            config.routes.get("/posts/{id}/edit", PostsController::edit);
                            config.routes.patch("/posts/{id}", PostsController::update);
                            config.routes.delete("/posts", PostsController::destroy);

                            config.routes.get("/cars", CarsController::index);
                            config.routes.get("/cars/build", CarsController::build);
                            config.routes.get("/cars/{id}", CarsController::show);
                            config.routes.post("/cars", CarsController::create);

                            config.routes.get(
                                    "/",
                                    ctx -> {
                                        var visited = Boolean.valueOf(ctx.cookie("visited"));
                                        var page =
                                                new MainPage(
                                                        visited,
                                                        ctx.sessionAttribute("currentUser"));
                                        ctx.render("index.jte", Map.of("page", page));
                                        ctx.cookie("visited", String.valueOf(true));
                                    });

                            config.routes.get(
                                    NamedRoutes.buildUserPath(),
                                    ctx -> {
                                        var page = new BuildUserPage();
                                        ctx.render("users/build.jte", Map.of("page", page));
                                    });

                            config.routes.get("/users/{id}", UsersController::show);

                            config.routes.post(
                                    NamedRoutes.usersPath(),
                                    ctx -> {
                                        var name = ctx.formParam("name").trim();
                                        var email = ctx.formParam("email").trim().toLowerCase();

                                        try {
                                            var passwordConfirmation =
                                                    ctx.formParam("passwordConfirmation");
                                            var password =
                                                    ctx.formParamAsClass("password", String.class)
                                                            .check(
                                                                    value ->
                                                                            value.equals(
                                                                                    passwordConfirmation),
                                                                    "Passwords are not the same")
                                                            .check(
                                                                    value -> value.length() > 6,
                                                                    "Password is to short")
                                                            .get();
                                            var user = new User(name, email, password);
                                            UserRepository.save(user);
                                            ctx.redirect(NamedRoutes.usersPath());
                                        } catch (ValidationException e) {
                                            var page =
                                                    new BuildUserPage(name, email, e.getErrors());
                                            ctx.render("users/build.jte", Map.of("page", page));
                                        }
                                    });

                            config.routes.get(
                                    NamedRoutes.usersPath(),
                                    ctx -> {
                                        var users = UserRepository.getEntities();
                                        var page = new UsersPage(users);
                                        // Отдаем обратно url + query params
                                        ctx.render("users/index.jte", Map.of("page", page));
                                    });

                            config.routes.get(
                                    NamedRoutes.buildCoursePath(),
                                    ctx -> {
                                        ctx.render("courses/build.jte");
                                    });

                            config.routes.get(
                                    NamedRoutes.coursesPath(),
                                    ctx -> {
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

                                        ctx.render("courses/index.jte", Map.of("page", page));
                                    });

                            config.routes.get(
                                    NamedRoutes.coursePath("{id}"),
                                    ctx -> {
                                        var id = ctx.pathParamAsClass("id", Long.class).get();
                                        var course =
                                                CourseRepository.find(id)
                                                        .orElseThrow(
                                                                () ->
                                                                        new NotFoundResponse(
                                                                                "Entity with id = "
                                                                                        + id
                                                                                        + " not found"));
                                        var page = new CoursePage(course);
                                        ctx.render("courses/show.jte", Map.of("page", page));
                                    });

                            config.routes.post(
                                    NamedRoutes.coursesPath(),
                                    ctx -> {
                                        var name = ctx.formParam("name");
                                        var description = ctx.formParam("description");

                                        var course = new Course(name, description);
                                        CourseRepository.save(course);
                                        ctx.sessionAttribute("flash", "Course has been created!");
                                        ctx.redirect(NamedRoutes.coursesPath());
                                    });
                        });

        return app;
    }
}
