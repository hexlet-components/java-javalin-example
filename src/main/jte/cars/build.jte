@import org.example.hexlet.util.NamedRoutes

@template.layout.page(
    content = @`
        <form action="${NamedRoutes.carsPath()}" method="post">
            Make
            <input type="text" required name="make">
            Model
            <input type="text" required name="model">
            <input type="submit">
        </form>
    `
)

