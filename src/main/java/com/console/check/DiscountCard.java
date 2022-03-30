package com.console.check;

import com.console.check.collection.CustomArrayList;
import com.console.check.collection.CustomList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class DiscountCard {
    private int number;
    private String bonus;

    public static CustomList<DiscountCard> addCard(){
        CustomList<DiscountCard> cardList = new CustomArrayList<>();
        cardList.add(new DiscountCard(1, "SilverCard"));
        cardList.add(new DiscountCard(2, "StandardCard"));
        cardList.add(new DiscountCard(3, "GoldCard"));
        cardList.add(new DiscountCard(4, "SilverCard"));
        cardList.add(new DiscountCard(5, "StandardCard"));
        cardList.add(new DiscountCard(6, "GoldCard"));
        cardList.add(new DiscountCard(7, "SilverCard"));
        cardList.add(new DiscountCard(8, "StandardCard"));
        cardList.add(new DiscountCard(9, "GoldCard"));
        cardList.add(new DiscountCard(10, "StandardCard"));

        return cardList;
    }
}
