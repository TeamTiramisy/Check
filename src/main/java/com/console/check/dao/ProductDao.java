package com.console.check.dao;

import com.console.check.entity.Card;
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
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao implements Dao<Integer, Product> {

    private static final ProductDao INSTANCE = new ProductDao();

    private static final String FIND_ALL = """
            SELECT id, qua, name, cost, promo FROM product 
            LIMIT ? 
            OFFSET ?
            """;

    private static final String FIND_BY_ID = """
            SELECT id, qua, name, cost, promo FROM product where id = ?
            """;

    private static final String DELETE = """
            DELETE FROM product WHERE id = ?
            """;

    private static final String UPDATE = """
            UPDATE product 
            SET qua = ?,
            name = ?,
            cost = ?,
            promo = ?
            WHERE id = ?
            """;

    private static final String SAVE = """
            INSERT INTO product (qua, name, cost, promo) 
            VALUES (?, ?, ?, ?) 
            """;

    @SneakyThrows
    public List<Product> findAll(Integer size, Integer page) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            preparedStatement.setObject(1, size);
            preparedStatement.setObject(2, page);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(buildProduct(resultSet));
            }

            return products;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Product> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;

            if (resultSet.next()) {
                product = buildProduct(resultSet);
            }
            return Optional.ofNullable(product);
        }
    }

    @SneakyThrows
    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setObject(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    @Override
    public void update(Product entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setObject(1, entity.getQua());
            preparedStatement.setObject(2, entity.getName());
            preparedStatement.setObject(3, entity.getCost());
            preparedStatement.setObject(4, entity.getPromo().name());
            preparedStatement.setObject(5, entity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    @Override
    public Product save(Product entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getQua());
            preparedStatement.setObject(2, entity.getName());
            preparedStatement.setObject(3, entity.getCost());
            preparedStatement.setObject(4, entity.getPromo().name());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
            return entity;
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
