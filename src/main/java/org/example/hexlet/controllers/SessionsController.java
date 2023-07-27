package org.example.hexlet.controllers;

import io.javalin.http.Context;

/**
 * SessionsController
 */
public class SessionsController {

    public static void build(Context ctx) {
        var nickname = ctx.formParam("nickname");
        var password = ctx.formParam("password");
    }

    public static void destroy(Context ctx) {
        var authCookie = ctx.cookie("auth");
    }
}
