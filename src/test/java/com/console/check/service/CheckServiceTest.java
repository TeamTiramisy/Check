package com.console.check.service;

import com.console.check.dto.ProductReadDto;
import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import com.console.check.util.Constants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.console.check.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class CheckServiceTest {

    private static final ProductReadDto APPLE = ProductReadDto.builder()
            .id(1)
            .qua(3)
            .name("Apple")
            .cost(1.65)
            .promo(Promo.NO)
            .build();

    private static final String[] IDS = {"1", "2", "3"};
    private static final String[] QUA = {"3", "2", "3"};

    private static List<ProductReadDto> products = null;
    private static final CheckService checkService = CheckService.getInstance();

    @BeforeAll()
    static void init() {
        products = checkService.addProducts();
    }


    @Test
    void findAllById() {
        List<ProductReadDto> product = checkService.findAllById(IDS, QUA);

        assertEquals(3, product.size());
        assertEquals(APPLE.getId(), product.get(0).getId());
        assertEquals(APPLE.getQua(), product.get(0).getQua());
        assertEquals(APPLE.getName(), product.get(0).getName());
        assertEquals(APPLE.getCost(), product.get(0).getCost());
        assertEquals(APPLE.getPromo(), product.get(0).getPromo());

        assertTrue(product.contains(APPLE));

    }

    @Test
    void addProducts() {

        assertEquals(9, products.size());
        assertEquals(APPLE.getId(), products.get(0).getId());
        assertEquals(APPLE.getQua(), products.get(0).getQua());
        assertEquals(APPLE.getName(), products.get(0).getName());
        assertEquals(APPLE.getCost(), products.get(0).getCost());
        assertEquals(APPLE.getPromo(), products.get(0).getPromo());

        assertTrue(products.contains(APPLE));
    }

    @Test
    void sum() {
        Double sum = checkService.sum(products);
        assertEquals(33.35, sum );
    }

    @Test
    void promoProducts() {
        int promoProducts = checkService.promoProducts(products);
        assertEquals(6, promoProducts);
    }

    @Test
    void getDiscount() {
        int discount = checkService.getDiscount(1);
        assertEquals(DISCOUNT_GOLD, discount);

        int discount2 = checkService.getDiscount(2);
        assertEquals(DISCOUNT_SILVER, discount2);

        int discount3 = checkService.getDiscount(3);
        assertEquals(DISCOUNT_STANDARD, discount3);

        int discount4 = checkService.getDiscount(9);
        assertEquals(DISCOUNT_NOT, discount4);
    }

    @Test
    void getTotal() {
        double sum = checkService.sum(products);
        double total = checkService.getTotal(sum, 7, 0.1);
        assertEquals(27.68, total);
    }

    @AfterAll
    static void delete(){
        products = null;
    }
}