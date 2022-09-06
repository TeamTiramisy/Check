package com.console.check.controller;

import com.console.check.dto.CardCreateDto;
import com.console.check.dto.CardReadDto;
import com.console.check.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardRestController {

    private final CardService cardService;

    @GetMapping
    public List<CardReadDto> findAll(Pageable pageable) {
        return cardService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CardReadDto findById(@PathVariable Integer id) {
        return cardService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardReadDto create(@RequestBody CardCreateDto card) {
        return cardService.save(card);
    }

    @PutMapping("/{id}")
    public CardReadDto update(@PathVariable Integer id,
                              @RequestBody CardCreateDto card) {
        return cardService.update(id, card);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        cardService.delete(id);
    }
}
