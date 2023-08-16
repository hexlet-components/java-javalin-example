package org.example.hexlet.controller;

import java.util.Collections;

import org.example.hexlet.dto.posts.PostPage;
import org.example.hexlet.dto.posts.PostsPage;
import org.example.hexlet.model.Post;
import org.example.hexlet.repository.PostRepository;
import org.example.hexlet.util.NamedRoutes;

import io.javalin.http.Context;

/**
 * PostsController.
 */
public class PostsController {
    public static void index(Context ctx) {
        var posts = PostRepository.getEntities();
        var page = new PostsPage(posts);
        ctx.render("posts/index.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var post = PostRepository.find(id);
        var page = new PostPage(post);
        ctx.render("posts/show.jte", Collections.singletonMap("page", page));
    }

    public static void build(Context ctx) {
        ctx.render("posts/build.jte");
    }

    public static void create(Context ctx) {
        var name = ctx.formParam("title");
        var description = ctx.formParam("body");

        var post = new Post(name, description);
        PostRepository.save(post);
        ctx.redirect(NamedRoutes.postsPath());
    }

    public static void edit(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var post = PostRepository.find(id);
        var page = new PostPage(post);
        ctx.render("posts/edit.jte", Collections.singletonMap("page", page));
    }


    public static void update(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();

        var title = ctx.formParam("title");
        var body = ctx.formParam("body");

        var post = PostRepository.find(id);
        post.setTitle(title);
        post.setBody(body);
        PostRepository.save(post);
        ctx.redirect(NamedRoutes.postsPath());
    }

    public static void destroy(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        PostRepository.delete(id);
        ctx.redirect(NamedRoutes.postsPath());
    }
}
