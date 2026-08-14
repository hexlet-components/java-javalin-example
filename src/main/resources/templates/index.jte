@import org.example.hexlet.dto.MainPage
@param MainPage page

@template.layout.page(
    content = @`
        <h1 class="text-body-emphasis">Hi Hexlet!</h1>
        <p>Javalin + jte</p>
        @if(!page.isVisited())
            This message is showed just once. To see it again, remove cookies
        @endif
        @if(page.getCurrentUser() != null)
          Welcome, ${page.getCurrentUser()}. if you want to sign out remove cookie called JSESSIONID 
        @endif
    `
)
