package com.console.check.service;


import com.console.check.dto.CardCreateDto;
import com.console.check.dto.CardReadDto;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.CardCreateMapper;
import com.console.check.mapper.CardReadMapper;

import com.console.check.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final CardReadMapper mapper;
    private final CardCreateMapper createMapper;


    public List<CardReadDto> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable).stream()
                .map(mapper::map)
                .toList();
    }

    public CardReadDto findById(Integer id) {
        return cardRepository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public boolean delete(Integer id) {
        return cardRepository.findById(id)
                .map(card -> {
                    cardRepository.delete(card);
                    cardRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public CardReadDto update(Integer id, CardCreateDto cardCreateDto) {
        return cardRepository.findById(id)
                .map(card -> createMapper.map(card, cardCreateDto))
                .map(cardRepository::saveAndFlush)
                .map(mapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @Transactional
    public CardReadDto save(CardCreateDto cardCreateDto) {
        return Optional.of(cardCreateDto)
                .map(createMapper::map)
                .map(cardRepository::save)
                .map(mapper::map)
                .orElseThrow();
    }
}
