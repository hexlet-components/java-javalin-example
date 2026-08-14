@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
    page = page,
    content = @`
        <h1>Courses List</h1>
        <form action="/courses" method="get">
            <input type="text" required name="term" value="${page.getTerm()}">
            <input type="submit" value="Search">
        </form>
        <a href="${NamedRoutes.buildCoursePath()}">New Course</a>
        @if(page.getCourses().isEmpty())
            <p>Courses list is empty</p>
        @else
            @for(var course : page.getCourses())
               <div>
                   <h2><a href="${NamedRoutes.coursePath(course.getId())}">${course.getName()}</a></h2>
                   <p>${course.getDescription()}</p>
               </div>
            @endfor
        @endif
    `
)
