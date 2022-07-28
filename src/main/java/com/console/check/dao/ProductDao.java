package com.console.check.dao;

import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import com.console.check.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao {

    private static final ProductDao INSTANCE = new ProductDao();

    private static final String FIND_ALL = """
            SELECT * FROM product
            """;

    @SneakyThrows
    public List<Product> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> technics = new ArrayList<>();

            while (resultSet.next()) {
                technics.add(buildProduct(resultSet));
            }

            return technics;
        }
    }

    @SneakyThrows
    private Product buildProduct(ResultSet resultSet) {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getInt("qua"),
                resultSet.getString("name"),
                resultSet.getDouble("cost"),
                Promo.valueOf(resultSet.getString("promo"))
        );
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
