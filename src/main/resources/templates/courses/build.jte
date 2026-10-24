@import org.example.hexlet.util.NamedRoutes

@template.layout.page(
    content = @`
        <form action="${NamedRoutes.coursesPath()}" method="post">
            <label>
                Name
                <input type="text" name="name">
            </label>
            <label>
                Description
                <input type="text" name="description">
            </label>
            <input type="submit">
        </form>
    `
)
