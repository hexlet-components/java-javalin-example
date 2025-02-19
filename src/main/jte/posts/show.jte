@import org.example.hexlet.dto.posts.PostPage
@param PostPage page

@template.layout.page(
    content = @`
        <h1>${page.getPost().getTitle()}</h1>
        <p>${page.getPost().getBody()}</p>
    `
)
