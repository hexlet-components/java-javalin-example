@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.UsersPage
@param UsersPage page

@template.layout.page(
    content = @`
        <a href="${NamedRoutes.buildUserPath()}">New User</a>
        @for(var entry : page.users)
            <div>${entry}</div>
        @endfor
    `
)
