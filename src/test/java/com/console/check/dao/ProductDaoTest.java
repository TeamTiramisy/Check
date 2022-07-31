package com.console.check.dao;

import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import com.console.check.util.Constants;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.console.check.util.Constants.DEFAULT_SIZE_PAGE;
import static com.console.check.util.Constants.NUMBER_PAGE;
import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    private final ProductDao productDao = ProductDao.getInstance();

    private static final Product APPLE = new Product(1, 3, "Apple", 1.65, Promo.NO);

    @Test
    void findAll(){
        List<Product> products = productDao.findAll(DEFAULT_SIZE_PAGE, NUMBER_PAGE);

        assertEquals(9, products.size());

        assertTrue(products.contains(APPLE));

        assertEquals(APPLE.getId(), products.get(0).getId());
        assertEquals(APPLE.getQua(), products.get(0).getQua());
        assertEquals(APPLE.getName(), products.get(0).getName());
        assertEquals(APPLE.getCost(), products.get(0).getCost());
        assertEquals(APPLE.getPromo(), products.get(0).getPromo());
    }

}