package org.example.hexlet.repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.hexlet.model.Car;

public class CarRepository extends BaseRepository {
    public static void save(Car car) throws SQLException {
        var sql = "INSERT INTO cars (make, model, created_at) VALUES (?, ?, ?)";
        try (var conn = dataSource.getConnection();
                var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, car.getMake());
            preparedStatement.setString(2, car.getModel());
            var createdAt = Instant.now();
            preparedStatement.setTimestamp(3, Timestamp.from(createdAt));

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getLong(1));
                car.setCreatedAt(createdAt);

            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Car> find(Long id) throws SQLException {
        var sql = "SELECT * FROM cars WHERE id = ?";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var make = resultSet.getString("make");
                var model = resultSet.getString("model");
                var createdAt = resultSet.getTimestamp("created_at").toInstant();

                var car = new Car(make, model);
                car.setId(id);
                car.setCreatedAt(createdAt);
                return Optional.of(car);
            }
            return Optional.empty();
        }
    }

    public static List<Car> getEntities() throws SQLException {
        var sql = "SELECT * FROM cars";
        try (var conn = dataSource.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Car>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var make = resultSet.getString("make");
                var model = resultSet.getString("model");
                var createdAt = resultSet.getTimestamp("created_at").toInstant();

                var car = new Car(make, model);
                car.setId(id);
                car.setCreatedAt(createdAt);
                result.add(car);
            }
            return result;
        }
    }
}
