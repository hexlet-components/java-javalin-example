@import org.example.hexlet.util.NamedRoutes

@template.layout.page(
    content = @`
        <form action="${NamedRoutes.coursesPath()}" method="post">
            <lable>
                Name
                <input type="text" name="name">
            </lable>
            <lable>
                Description
                <input type="text" name="description">
            </lable>
            <input type="submit">
        </form>
    `
)
