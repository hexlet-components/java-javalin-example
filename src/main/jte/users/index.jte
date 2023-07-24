@import org.example.hexlet.data.UsersPage
@param UsersPage page

HELLO JTE

@for(var entry : page.users())
   <div>${entry}</div>
@endfor

