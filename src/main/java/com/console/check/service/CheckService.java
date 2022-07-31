package com.console.check.service;

import com.console.check.dao.CardDao;
import com.console.check.dao.ProductDao;
import com.console.check.dto.ProductReadDto;
import com.console.check.entity.Card;
import com.console.check.entity.Promo;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.ProductReadMapper;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.console.check.util.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CheckService {



    private static final CheckService INSTANCE = new CheckService();

    private final ProductDao productDao = ProductDao.getInstance();
    private final CardDao cardDao = CardDao.getInstance();

    private final ProductReadMapper mapper = ProductReadMapper.getInstance();


    public List<ProductReadDto> findAllById(String[] ids){
        List<ProductReadDto> products = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            ProductReadDto product = productDao.findById(Integer.valueOf(ids[i]))
                    .map(mapper::map)
                    .orElseThrow(() -> new WrongIdException("Invalid id"));
            products.add(product);
        }
        return products;
    }


    public List<ProductReadDto> addProducts(){
        return productDao.findAll(20, 0).stream()
                .map(mapper::map)
                .toList();
    }



    public double sum(List<ProductReadDto> products) {
        Optional<Double> total = products.stream()
                .map(product -> product.getQua() * product.getCost())
                .reduce(Double::sum);
        return total.orElseThrow();
    }



    public int promoProducts(List<ProductReadDto> products) {
        int count = (int) products.stream()
                .filter(product -> product.getPromo().equals(Promo.YES))
                .count();

        return count;
    }



    public int getDiscount(Integer id) {
        int discount = 0;
        Card card = cardDao.findById(id)
                .orElse(new Card(11, ""));

        switch (card.getBonus()) {
            case STANDARD -> discount = 3;
            case SILVER -> discount = 5;
            case GOLD -> discount = 7;
        }

        return discount;
    }



    public double getTotal(double sum, int discount, double promoDiscount) {
        sum -= sum * (discount / 100.0 + promoDiscount);
        sum = (double) Math.round(sum * 100) / 100;
        return sum;
    }

    public static CheckService getInstance() {
        return INSTANCE;
    }
}
