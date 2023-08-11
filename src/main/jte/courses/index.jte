@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.CoursesPage
@param CoursesPage page

<html>
    <head>
        <title>Хекслет</title>
    </head>
    <body>
        <h1>Список Курсов</h1>
        @if(page.courses().isEmpty())
            <p>Пока не добавлено ни одного курса</p>
        @else
            @for(var course : page.courses())
               <div>
                   <h2><a href="${NamedRoutes.coursePath(course.getId())}">${course.getName()}</a></h2>
                   <p>${course.getDescription()}</p>
               </div>
            @endfor
        @endif
    </body>
</html>
