package com.console.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiscountCard {
    private int number;
    private String bonus;

    public DiscountCard(int number, String bonus) {
        this.number = number;
        this.bonus = bonus;
    }

    public int getNumber() {
        return number;
    }

    public String getBonus() {
        return bonus;
    }

    public static List<DiscountCard> addCard(){
        List<DiscountCard> cardList = new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return number == that.number && Objects.equals(bonus, that.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, bonus);
    }
}
