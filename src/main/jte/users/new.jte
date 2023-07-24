@import org.example.hexlet.data.NewUserPage
@param NewUserPage page

@if(page.e() != null)
    <ul>
        @for(var validator : page.e().getErrors().values())
            @for(var error : validator)
                <li>${error.getMessage()}</li>
            @endfor
        @endfor
    </ul>
@endif

<form action="/users" method="post">
  <div>
    <label>
      Имя
      <input type="text" name="name" value="${page.name()}" />
    </label>
  </div>
  <div>
    <label>
      Email
      <input type="email" required name="email" value="${page.email()}" />
    </label>
  </div>
  <div>
    <label>
      Пароль
      <input type="password" required name="password" />
    </label>
  </div>
  <div>
    <label>
      Подтверждение пароля
      <input type="password" required name="passwordConfirmation" />
    </label>
  </div>
  <input type="submit" value="Зарегистрировать" />
</form>

