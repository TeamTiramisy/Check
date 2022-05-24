package com.console.check.service;

import com.console.check.CheckRunner;
import com.console.check.WrongIdException;
import com.console.check.products.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckServiceTest {

    private static final Product apple = new Product(3, "Яблоко", 1.65);
    private static final String file = "src/test/resources/productsTest.txt";
    private static List<Product> products = null;

    @BeforeAll()
    static void init() throws WrongIdException, IOException {
        products = CheckService.addProducts(file);
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
        double sum = CheckService.sum(products);
        assertEquals(33.35, sum );
    }

    @Test
    void promoProductsTest(){
        long promoProducts = CheckService.promoProducts(products);
        assertEquals(6, promoProducts);
    }

    @Test
    void getDiscountTest(){
        int discount = CheckService.getDiscount(6);
        assertEquals(7, discount);

        int discount2 = CheckService.getDiscount(4);
        assertEquals(5, discount2);

        int discount3 = CheckService.getDiscount(8);
        assertEquals(3, discount3);

        int discount4 = CheckService.getDiscount(11);
        assertEquals(0, discount4);
    }

    @Test
    void getTotalTest(){
        double sum = CheckService.sum(products);
        double total = CheckService.getTotal(sum, 7, 0.1);
        assertEquals(27.68, total);
    }

    @AfterAll
    static void delete(){
        products = null;
    }

}