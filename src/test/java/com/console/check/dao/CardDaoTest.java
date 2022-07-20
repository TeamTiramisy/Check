package com.console.check.dao;

import com.console.check.entity.Card;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardDaoTest {

    private static final Integer ID = 1;
    private static final Integer ID_FAILED = 99;
    private static final String BONUS = "GoldCard";
    private final CardDao cardDao = CardDao.getInstance();

    @Test
    void findByIdTest(){
        Optional<Card> maybeCard = cardDao.findById(ID);
        Optional<Card> failedCard = cardDao.findById(ID_FAILED);

        assertTrue(maybeCard.isPresent());

        assertFalse(failedCard.isPresent());

        maybeCard.ifPresent(card -> card.getBonus().equals(BONUS));
    }

}