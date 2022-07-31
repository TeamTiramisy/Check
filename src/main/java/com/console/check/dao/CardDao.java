package com.console.check.dao;

import com.console.check.entity.Card;
import com.console.check.entity.Product;
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
public class CardDao implements Dao<Integer, Card> {

    private static final CardDao INSTANCE = new CardDao();

    private static final String FIND_ALL = """
            SELECT id, bonus FROM card 
            LIMIT ? 
            OFFSET ?
            """;

    private static final String FIND_BY_ID = """
            SELECT id, bonus FROM card WHERE id = ?
            """;

    private static final String DELETE = """
            DELETE FROM card WHERE id = ?
            """;

    private static final String UPDATE = """
            UPDATE card 
            SET bonus = ?
            WHERE id = ?
            """;

    private static final String SAVE = """
            INSERT INTO card (bonus) 
            VALUES (?) 
            """;

    @SneakyThrows
    @Override
    public List<Card> findAll(Integer size, Integer page) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            preparedStatement.setObject(1, size);
            preparedStatement.setObject(2, page);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Card> cards = new ArrayList<>();

            while (resultSet.next()) {
                cards.add(buildCard(resultSet));
            }

            return cards;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Card> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Card card = null;

            if (resultSet.next()) {
                card = buildCard(resultSet);
            }
            return Optional.ofNullable(card);
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
    public void update(Card entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setObject(1, entity.getBonus());
            preparedStatement.setObject(2, entity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    @Override
    public Card save(Card entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getBonus());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
            return entity;
        }
    }

    @SneakyThrows
    private Card buildCard(ResultSet resultSet) {
        return new Card(
                resultSet.getInt("id"),
                resultSet.getString("bonus")
        );
    }


    public static CardDao getInstance() {
        return INSTANCE;
    }
}
