package org.example.hexlet.dto.cars;

import java.util.List;

import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.Car;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarsPage extends BasePage {
    private List<Car> cars;
}

