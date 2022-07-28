package com.console.check.service;

import com.console.check.entity.Card;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.console.check.constants.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DiscountService {

    private static final DiscountService INSTANCE = new DiscountService();

    public List<Card> addCard(){
        List<Card> cardList = new ArrayList<>();
        cardList.add(new Card(1, SILVER));
        cardList.add(new Card(2, STANDARD));
        cardList.add(new Card(3, GOLD));
        cardList.add(new Card(4, SILVER));
        cardList.add(new Card(5, STANDARD));
        cardList.add(new Card(6, GOLD));
        cardList.add(new Card(7, SILVER));
        cardList.add(new Card(8, STANDARD));
        cardList.add(new Card(9, GOLD));
        cardList.add(new Card(10, STANDARD));

        return cardList;
    }

    public static DiscountService getInstance() {
        return INSTANCE;
    }
}
