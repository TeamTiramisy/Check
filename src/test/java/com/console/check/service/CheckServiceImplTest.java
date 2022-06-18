package com.console.check.service;

import com.console.check.entity.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckServiceImplTest {

    private static final Product apple = new Product(3, "Яблоко", 1.65);
    private static final String file = "src/test/resources/productsTest.txt";
    private static List<Product> products = null;
    private static final CheckServiceImpl checkService = CheckServiceImpl.getInstance();

    @BeforeAll()
    static void init() throws IOException {
        products = checkService.addProducts(file);
    }


    @Test
    void addProductsTest() {
        assertEquals(9, products.size());
        assertEquals(apple.getQua(), products.get(0).getQua());
        assertEquals(apple.getName(), products.get(0).getName());
        assertEquals(apple.getCost(), products.get(0).getCost());
        assertEquals(apple, products.get(0));
    }

    @Test
    void sumTest(){
        double sum = checkService.sum(products);
        assertEquals(33.35, sum );
    }

    @Test
    void promoProductsTest(){
        long promoProducts = checkService.promoProducts(products);
        assertEquals(6, promoProducts);
    }

    @Test
    void getDiscountTest(){
        int discount = checkService.getDiscount(6);
        assertEquals(7, discount);

        int discount2 = checkService.getDiscount(4);
        assertEquals(5, discount2);

        int discount3 = checkService.getDiscount(8);
        assertEquals(3, discount3);

        int discount4 = checkService.getDiscount(11);
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