@import org.example.hexlet.dto.users.UserPage;
@param UserPage page

@template.layout.page(
content = @`
    <div>${page.getUser().getName()}</div>
    <div>${page.getUser().getEmail()}</div>
`
)