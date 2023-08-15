@import org.example.hexlet.util.NamedRoutes
@import gg.jte.Content
@param Content content

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
    </p>
    ${content}
  </body>
</html>
