package org.example.hexlet.controller;

import java.sql.SQLException;

import org.example.hexlet.dto.cars.CarPage;
import org.example.hexlet.dto.cars.CarsPage;
import org.example.hexlet.model.Car;
import org.example.hexlet.repository.CarRepository;
import org.example.hexlet.util.NamedRoutes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import static io.javalin.rendering.template.TemplateUtil.model;

public class CarsController {
    public static void index(Context ctx) throws SQLException {
        var cars = CarRepository.getEntities();
        var page = new CarsPage(cars);
        ctx.render("cars/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var car = CarRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new CarPage(car);
        ctx.render("cars/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        ctx.render("cars/build.jte");
    }

    public static void create(Context ctx) throws SQLException {
        var make = ctx.formParam("make");
        var model = ctx.formParam("model");

        var car = new Car(make, model);
        CarRepository.save(car);
        ctx.redirect(NamedRoutes.carsPath());
    }
}
