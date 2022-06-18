package com.console.check.service;

import com.console.check.entity.Product;

import java.io.IOException;
import java.util.List;

public interface CheckService {

    List<Product> addProducts(String file) throws IOException;

    double sum(List<Product> products);

    long promoProducts(List<Product> products);

    int getDiscount(int numberCard);

    double getTotal(double sum, int discount, double promoDiscount);

}
