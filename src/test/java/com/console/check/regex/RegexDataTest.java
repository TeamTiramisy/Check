package com.console.check.regex;

import com.console.check.products.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexDataTest {

    private static final String parameters = "1;Яблоко;1.65;3\n" +
            "2;Молоко;1.15;1\n" +
            "6;Хлеб;10.1;1\n" +
            "6;Хлеб;10.1;-1\n" +
            "7;Рыба;10.n0;1";

    private static List<String> validation = null;

    @BeforeAll
    static void init() throws IOException {
        validation = RegexData.validation(parameters);
    }

    @Test
    void validationTest() {
        Assertions.assertEquals(validation.size(), 2);


    }

    @ParameterizedTest
    @ValueSource(strings = {"7;Рыба;10.n0;1", "6;Хлеб;10.1;1", "6;Хлеб;10.1;-1"})
    void inValidationTest(String product) throws IOException {
        boolean isValid = validation.contains(product);
        assertFalse(isValid);
    }

    @AfterAll
    static void deleteValidation(){
        validation = null;
    }
}