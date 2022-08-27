package com.console.check.service;


import com.console.check.dto.CardCreateDto;
import com.console.check.dto.CardReadDto;
import com.console.check.entity.Card;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.CardCreateMapper;
import com.console.check.mapper.CardReadMapper;

import com.console.check.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.console.check.util.Constants.DEFAULT_SIZE_PAGE;
import static com.console.check.util.Constants.NUMBER_PAGE;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final CardReadMapper mapper;
    private final CardCreateMapper createMapper;


    public List<CardReadDto> findAll(String size, String page){
        int pageSize = size != null ? Integer.parseInt(size) : DEFAULT_SIZE_PAGE;
        int pageNumber = page != null ? (Integer.parseInt(page)) : NUMBER_PAGE;

        return cardRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream()
                .map(mapper::map)
                .toList();
    }

    public CardReadDto findById(Integer id){
        return cardRepository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new WrongIdException("Invalid id"));
    }

    @Transactional
    public boolean delete(Integer id){
        return cardRepository.findById(id)
                .map(card -> {
                    cardRepository.delete(card);
                    cardRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public CardReadDto update(Integer id, CardCreateDto cardCreateDto){
        return cardRepository.findById(id)
                .map(card -> createMapper.map(card, cardCreateDto))
                .map(cardRepository::saveAndFlush)
                .map(mapper::map)
                .orElseThrow(() -> new WrongIdException("Invalid id"));

    }

    @Transactional
    public CardReadDto save(CardCreateDto cardCreateDto){
        return Optional.of(cardCreateDto)
                .map(createMapper::map)
                .map(cardRepository::save)
                .map(mapper::map)
                .orElseThrow();
    }
}
