package com.console.check.service;

import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckServiceDBTest {

    private static final Product APPLE = new Product(1, 3, "Яблоко", 1.65, Promo.NO);

    private static List<Product> products = null;
    private static final CheckServiceDB checkService = CheckServiceDB.getInstance();

    @BeforeAll()
    static void init() throws IOException {
        products = checkService.addProducts();
    }

    @Test
    void addProductsTest() {
        assertEquals(9, products.size());
        assertEquals(APPLE.getId(), products.get(0).getId());
        assertEquals(APPLE.getQua(), products.get(0).getQua());
        assertEquals(APPLE.getName(), products.get(0).getName());
        assertEquals(APPLE.getCost(), products.get(0).getCost());
        assertEquals(APPLE.getPromo(), products.get(0).getPromo());

        assertTrue(products.contains(APPLE));
    }

    @Test
    void sumTest(){
        Double sum = checkService.sum(products);
        assertEquals(33.35, sum );
    }

    @Test
    void promoProductsTest(){
        int promoProducts = checkService.promoProducts(products);
        assertEquals(6, promoProducts);
    }

    @Test
    void getDiscountTest(){
        int discount = checkService.getDiscount(1);
        assertEquals(7, discount);

        int discount4 = checkService.getDiscount(2);
        assertEquals(0, discount4);
    }

    @Test
    void getTotalTest(){
        double sum = checkService.sum(products);
        double total = checkService.getTotal(sum, 7, 0.1);
        assertEquals(27.68, total);
    }

    @AfterAll
    static void delete(){
        products = null;
    }

}