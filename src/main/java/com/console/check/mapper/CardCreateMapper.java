package com.console.check.mapper;

import com.console.check.dto.CardCreateDto;
import com.console.check.entity.Card;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CardCreateMapper implements Mapper<CardCreateDto, Card>{

    private static final CardCreateMapper INSTANCE = new CardCreateMapper();

    @Override
    public Card map(CardCreateDto object) {
        return Card.builder()
                .bonus(object.getBonus())
                .build();
    }

    public static CardCreateMapper getInstance() {
        return INSTANCE;
    }
}
