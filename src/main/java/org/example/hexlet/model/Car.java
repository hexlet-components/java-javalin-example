package org.example.hexlet.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class Car {
    private Long id;
    private String make;
    private String model;
    private LocalDateTime createdAt;

    public Car(String make, String model) {
        this.make = make;
        this.model = model;
    }

    public String getName() {
        return make + " " + model;
    }
}
