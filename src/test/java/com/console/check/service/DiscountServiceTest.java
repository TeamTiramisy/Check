package com.console.check.service;


import com.console.check.entity.Card;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {

    private static final DiscountService discountService = DiscountService.getInstance();
    private final Card card = new Card(1, "SilverCard");

    private static List<Card> cardList = null;

    @BeforeAll
    static void init() {
        cardList = discountService.addCard();
    }

    @Test
    void addCardTest() {
        assertEquals(cardList.size(), 10);
        assertEquals(card.getId(), cardList.get(0).getId());
        assertEquals(card.getBonus(), cardList.get(0).getBonus());
        assertEquals(card, cardList.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"SilverCard", "StandardCard", "GoldCard"})
    void cardBonusTest(String card) {
        List<String> bonus = cardList.stream()
                .map(Card::getBonus).toList();
        assertTrue(bonus.contains(card));
    }

    @AfterAll
    static void delete(){
        cardList = null;
    }
}