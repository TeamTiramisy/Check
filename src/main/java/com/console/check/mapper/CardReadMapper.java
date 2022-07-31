package com.console.check.mapper;

import com.console.check.dto.CardReadDto;
import com.console.check.entity.Card;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CardReadMapper implements Mapper<Card, CardReadDto>{

    private static final CardReadMapper INSTANCE = new CardReadMapper();

    @Override
    public CardReadDto map(Card object) {
        return CardReadDto.builder()
                .id(object.getId())
                .bonus(object.getBonus())
                .build();
    }

    public static CardReadMapper getInstance() {
        return INSTANCE;
    }

}
