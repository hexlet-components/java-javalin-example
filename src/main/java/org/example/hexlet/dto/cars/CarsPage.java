package org.example.hexlet.dto.cars;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.Car;

@AllArgsConstructor
@Getter
public class CarsPage extends BasePage {
    private List<Car> cars;
}
