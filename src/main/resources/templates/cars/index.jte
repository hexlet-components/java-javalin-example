@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.cars.CarsPage
@param CarsPage page

@template.layout.page(
    page = page,
    content = @`
        <h1>Cars List</h1>
        <a href="${NamedRoutes.buildCarPath()}">New Car</a>
        @if(page.getCars().isEmpty())
            <p>Cars list is empty</p>
        @else
            @for(var car : page.getCars())
               <div>
                   <h2>
                       <a href="${NamedRoutes.carPath(car.getId())}">${car.getName()}</a>
                   </h2>
               </div>
            @endfor
        @endif
    `
)

