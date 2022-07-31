package com.console.check.service;

import com.console.check.dao.CardDao;
import com.console.check.dto.CardCreateDto;
import com.console.check.dto.CardReadDto;
import com.console.check.entity.Card;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.CardCreateMapper;
import com.console.check.mapper.CardReadMapper;
import com.console.check.util.Constants;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.console.check.util.Constants.DEFAULT_SIZE_PAGE;
import static com.console.check.util.Constants.NUMBER_PAGE;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CardService implements Service<Integer, CardReadDto, CardCreateDto> {

    private static final CardService INSTANCE = new CardService();

    private final CardDao cardDao = CardDao.getInstance();
    private final CardReadMapper mapper = CardReadMapper.getInstance();
    private final CardCreateMapper createMapper = CardCreateMapper.getInstance();

    @Override
    public List<CardReadDto> findAll(String size, String page){
        int pageSize = size != null ? Integer.parseInt(size) : DEFAULT_SIZE_PAGE;
        int pageNumber = page != null ? (Integer.parseInt(page) * pageSize) : NUMBER_PAGE;


        return cardDao.findAll(pageSize, pageNumber).stream()
                .map(mapper::map)
                .toList();
    }

    public CardReadDto findById(Integer id){
        return cardDao.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new WrongIdException("Invalid id"));
    }

    public boolean delete(Integer id){
        return cardDao.delete(id);
    }

    public void update(Integer id, CardCreateDto cardCreateDto){
        Card card = cardDao.findById(id).orElseThrow();

        if (cardCreateDto.getBonus() != null){
            card.setBonus(cardCreateDto.getBonus());
        }


        card.setId(id);

        cardDao.update(card);

    }

    public CardReadDto save(CardCreateDto cardCreateDto){
        Card card = cardDao.save(createMapper.map(cardCreateDto));
        return mapper.map(card);
    }

    public static CardService getInstance() {
        return INSTANCE;
    }
}
