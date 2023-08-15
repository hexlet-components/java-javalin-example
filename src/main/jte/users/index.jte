@import org.example.hexlet.dto.UsersPage
@param UsersPage page

@template.layout.page(
    content = @`
        @for(var entry : page.users())
            <div>${entry}</div>
        @endfor
    `
)
