package com.console.check.service;

import com.console.check.repository.CardRepository;
import com.console.check.dto.ProductReadDto;
import com.console.check.entity.Card;
import com.console.check.entity.Promo;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.ProductReadMapper;

import com.console.check.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


import static com.console.check.util.Constants.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckService {

    private final ProductRepository productRepository;
    private final CardRepository cardRepository;

    private final ProductReadMapper mapper;


    public List<ProductReadDto> findAllById(String[] ids, String[] qua){

        if (ids.length != qua.length){
            throw new WrongIdException("Missing id or quantity product");
        }

        List<ProductReadDto> products = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            ProductReadDto product = productRepository.findById(Integer.valueOf(ids[i]))
                    .map(mapper::map)
                    .orElseThrow(() -> new WrongIdException("Invalid id"));

            ProductReadDto productReadDto = mapper.copy(product, Integer.valueOf(qua[i]));

            products.add(productReadDto);
        }
        return products;
    }


    public List<ProductReadDto> addProducts(){
        return productRepository.findAll().stream()
                .map(mapper::map)
                .toList();
    }



    public double sum(List<ProductReadDto> products) {
        double total = products.stream()
                .map(product -> product.getQua() * product.getCost())
                .reduce(Double::sum).orElseThrow();
        total = (double) Math.round(total * 100) / 100;
        return total;
    }



    public int promoProducts(List<ProductReadDto> products) {
        int count = (int) products.stream()
                .filter(product -> product.getPromo().equals(Promo.YES))
                .count();

        return count;
    }



    public int getDiscount(Integer id) {
        int discount = DISCOUNT_NOT;
        Card card = cardRepository.findById(id)
                .orElse(cardRepository.findById(ID_NOT_BONUS).orElseThrow());

        switch (card.getBonus()) {
            case STANDARD -> discount = DISCOUNT_STANDARD;
            case SILVER -> discount = DISCOUNT_SILVER;
            case GOLD -> discount = DISCOUNT_GOLD;
        }

        return discount;
    }



    public double getTotal(double sum, int discount, double promoDiscount) {
        sum -= sum * (discount / 100.0 + promoDiscount);
        sum = (double) Math.round(sum * 100) / 100;
        return sum;
    }
}
