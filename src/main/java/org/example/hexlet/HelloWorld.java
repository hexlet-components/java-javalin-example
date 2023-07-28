package org.example.hexlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.hexlet.controllers.PostsController;
import org.example.hexlet.controllers.SessionsController;
import org.example.hexlet.data.CoursesPage;
import org.example.hexlet.data.MainPage;
import org.example.hexlet.data.NewUserPage;
import org.example.hexlet.data.UsersPage;
import org.example.hexlet.domain.Course;
import org.example.hexlet.domain.User;
import org.example.hexlet.lib.CourseRepository;
import org.example.hexlet.lib.UserRepository;

import io.javalin.Javalin;
// import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;

public class HelloWorld {
    public static void main(String[] args) {
        // JavalinJte.init();

        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });

        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
        });

        // app.get("/users", UsersController::index);
        // app.get("/users/{id}", UsersController::show);
        // app.get("/users/new", UsersController::build);
        // app.post("/users", UsersController::create);
        // app.get("/users/{id}/edit", UsersController::edit);
        // app.patch("/users/{id}", UsersController::update);
        // app.delete("/users", UsersController::destroy);

        app.get("/sessions/build", SessionsController::build);
        app.post("/sessions", SessionsController::create);
        app.delete("/sessions", SessionsController::destroy);

        app.get("/posts", PostsController::index);
        app.get("/posts/{id}", PostsController::show);
        app.get("/posts/new", PostsController::build);
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

        app.get("/courses/new", ctx -> {
            ctx.render("courses/new.jte");
        });

        // app.get(NamedRoutes.newUserPath(), ctx -> {
        //     ctx.render("users/new.jte");
        // });

        app.get(NamedRoutes.coursePath("{id}"), ctx -> {
            ctx.render("users/show.jte");
        });

        app.post(NamedRoutes.usersPath(), ctx -> {
            var name = ctx.formParam("name").trim();
            var email = ctx.formParam("email").toLowerCase();

            try {
                // var password = ctx.formParam("password");
                var passwordConfirmation = ctx.formParam("passwordConfirmation");
                var password = ctx.formParamAsClass("password", String.class)
                        .check(value -> value == passwordConfirmation, "Пароли не совпадают")
                        .check(value -> value.length() > 6, "У пароля недостаточная длина")
                        .get();
                var user = new User(name, email, password);
                UserRepository.save(user);
                ctx.redirect("/users");
            } catch (ValidationException e) {
                var page = new NewUserPage(name, email, e);
                ctx.render("users/new.jte", Collections.singletonMap("page", page));
            }
        });

        app.get("/users", ctx -> {
            var users = new String[] { "ivan", "peter" };
            var page = new UsersPage(users);
            // Отдаем обратно url + query params
            ctx.render("users/index.jte", Collections.singletonMap("page", page));
        });

        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");
            List<Course> courses;
            if (term != null) {
                // Фильтруем курсы в соответствии со значением term
                courses = new ArrayList<Course>();
            } else {
                courses = CourseRepository.getEntities();
            }
            var page = new CoursesPage(courses, term);

            ctx.render("courses/index.jte", Collections.singletonMap("page", page));
        });

        app.post("/courses", ctx -> {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            var course = new Course(name, description);
            CourseRepository.save(course);
            ctx.redirect("/courses");
        });

        app.start(7070);
    }
}
