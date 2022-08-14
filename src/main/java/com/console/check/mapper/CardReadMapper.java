package com.console.check.mapper;

import com.console.check.dto.CardReadDto;
import com.console.check.entity.Card;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
public class CardReadMapper implements Mapper<Card, CardReadDto>{

    @Override
    public CardReadDto map(Card object) {
        return CardReadDto.builder()
                .id(object.getId())
                .bonus(object.getBonus())
                .build();
    }
}
