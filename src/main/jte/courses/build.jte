@import org.example.hexlet.util.NamedRoutes

@template.layout.page(
    content = @`
        <form action="${NamedRoutes.coursesPath()}" method="post">
            Name
            <input type="text" name="name">
            Description
            <input type="text" name="description">
            <input type="submit">
        </form>
    `
)
