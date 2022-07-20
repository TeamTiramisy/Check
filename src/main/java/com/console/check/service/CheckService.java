package com.console.check.service;

import com.console.check.entity.Product;

import java.util.List;

public interface CheckService {

    List<Product> addProducts();

    double sum(List<Product> products);

    int promoProducts(List<Product> products);

    int getDiscount(Integer id);

    double getTotal(double sum, int discount, double promoDiscount);

}
