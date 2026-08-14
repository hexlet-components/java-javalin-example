@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.BasePage
@import gg.jte.Content
@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Hexlet Javalin Example</title>
    </head>
    <body>
        <p>
            <a href="${NamedRoutes.buildSessionPath()}">Sign in</a>
            <a href="${NamedRoutes.usersPath()}">Users</a>
            <a href="${NamedRoutes.coursesPath()}">Courses</a>
            <a href="${NamedRoutes.carsPath()}">Cars</a>
        </p>
        @if(page != null && page.getFlash() != null)
            <p>${page.getFlash()}</p>
        @endif
        ${content}
    </body>
</html>
