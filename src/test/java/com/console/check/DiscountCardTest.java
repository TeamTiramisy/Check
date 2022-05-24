package com.console.check;

import com.console.check.regex.RegexData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardTest {

    private  final DiscountCard card = new DiscountCard(1, "SilverCard");

    private static List<DiscountCard> cardList = null;

    @BeforeAll
    static void init() {
        cardList = DiscountCard.addCard();
    }

    @Test
    void addCardTest() {
        assertEquals(cardList.size(), 10);
        assertEquals(card.getNumber(), cardList.get(0).getNumber());
        assertEquals(card.getBonus(), cardList.get(0).getBonus());
        assertEquals(card, cardList.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"SilverCard", "StandardCard", "GoldCard"})
    void cardBonusTest(String card) {
        List<String> bonus = cardList.stream()
                .map(DiscountCard::getBonus).toList();
        assertTrue(bonus.contains(card));
    }

    @AfterAll
    static void delete(){
        cardList = null;
    }
}