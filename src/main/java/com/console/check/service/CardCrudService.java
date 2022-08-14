package com.console.check.service;

import com.console.check.dao.CardDao;
import com.console.check.dto.CardCreateDto;
import com.console.check.dto.CardReadDto;
import com.console.check.entity.Card;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.CardCreateMapper;
import com.console.check.mapper.CardReadMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.console.check.util.Constants.DEFAULT_SIZE_PAGE;
import static com.console.check.util.Constants.NUMBER_PAGE;


@Service
@RequiredArgsConstructor
public class CardCrudService implements CrudService<Integer, CardReadDto, CardCreateDto> {

    private final CardDao cardDao;
    private final CardReadMapper mapper;
    private final CardCreateMapper createMapper;

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
}
