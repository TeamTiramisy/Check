package com.console.check.dao;

import com.console.check.entity.Card;
import com.console.check.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardDao {

    private static final CardDao INSTANCE = new CardDao();

    private static final String FIND_BY_ID = """
            SELECT * FROM card where id = ?
            """;

    @SneakyThrows
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
