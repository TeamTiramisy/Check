package com.console.check.service;

import com.console.check.dao.CardDao;
import com.console.check.dao.ProductDao;
import com.console.check.entity.Card;
import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.console.check.constants.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CheckServiceDB implements CheckService {

    private final ProductDao productDao = ProductDao.getInstance();
    private final CardDao cardDao = CardDao.getInstance();

    private static final CheckServiceDB INSTANCE = new CheckServiceDB();

    @Override
    public List<Product> addProducts(){
        return productDao.findAll();
    }

    @Override
    public double sum(List<Product> products) {
        Optional<Double> total = products.stream()
                .map(product -> product.getQua() * product.getCost())
                .reduce(Double::sum);
        return total.orElseThrow();
    }

    @Override
    public int promoProducts(List<Product> products) {
        int count = (int) products.stream()
                .filter(product -> product.getPromo().equals(Promo.YES))
                .count();

        return count;
    }

    @Override
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

    @Override
    public double getTotal(double sum, int discount, double promoDiscount) {
        sum -= sum * (discount / 100.0 + promoDiscount);
        sum = (double) Math.round(sum * 100) / 100;
        return sum;
    }

    public static CheckServiceDB getInstance() {
        return INSTANCE;
    }
}
