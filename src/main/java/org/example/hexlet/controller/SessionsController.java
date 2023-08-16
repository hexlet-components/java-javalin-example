package org.example.hexlet.controller;

import io.javalin.http.Context;

/**
 * SessionsController.
 */
public class SessionsController {

    public static void build(Context ctx) {
        ctx.render("sessions/build.jte");
    }

    public static void create(Context ctx) {
        var nickname = ctx.formParam("nickname");
        // var password = ctx.formParam("password");

        // check password

        ctx.sessionAttribute("currentUser", nickname);
        ctx.redirect("/");
    }

    public static void destroy(Context ctx) {
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect("/");
    }
}
