package com.console.check.service;

import com.console.check.entity.DiscountCard;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.console.check.constants.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DiscountService {

    private static final DiscountService INSTANCE = new DiscountService();

    public List<DiscountCard> addCard(){
        List<DiscountCard> cardList = new ArrayList<>();
        cardList.add(new DiscountCard(1, SILVER));
        cardList.add(new DiscountCard(2, STANDARD));
        cardList.add(new DiscountCard(3, GOLD));
        cardList.add(new DiscountCard(4, SILVER));
        cardList.add(new DiscountCard(5, STANDARD));
        cardList.add(new DiscountCard(6, GOLD));
        cardList.add(new DiscountCard(7, SILVER));
        cardList.add(new DiscountCard(8, STANDARD));
        cardList.add(new DiscountCard(9, GOLD));
        cardList.add(new DiscountCard(10, STANDARD));

        return cardList;
    }

    public static DiscountService getInstance() {
        return INSTANCE;
    }
}
