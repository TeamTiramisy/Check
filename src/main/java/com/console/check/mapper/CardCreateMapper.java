package com.console.check.mapper;

import com.console.check.dto.CardCreateDto;
import com.console.check.entity.Card;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
public class CardCreateMapper implements Mapper<CardCreateDto, Card>{

    @Override
    public Card map(CardCreateDto object) {
        return Card.builder()
                .bonus(object.getBonus())
                .build();
    }
}
