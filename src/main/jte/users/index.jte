@import org.example.hexlet.dto.UsersPage
@param UsersPage page

HELLO JTE

@for(var entry : page.users())
   <div>${entry}</div>
@endfor

