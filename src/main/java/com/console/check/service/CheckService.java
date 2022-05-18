package com.console.check.service;

import com.console.check.DiscountCard;
import com.console.check.IdCostProducts;
import com.console.check.WrongIdException;
import com.console.check.products.Product;
import com.console.check.products.PromotionsProduct;
import com.console.check.regex.RegexData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckService {

    public static List<Product> addProducts(String file) throws IOException {
        String parameters = new String(Files.readAllBytes(Paths.get(file)));

        Stream<Product> productStream = RegexData.validation(parameters).stream()
                .map(string -> string.split(";"))
                .filter(product -> Integer.parseInt(product[0]) == IdCostProducts.ID_APPLE && product[1].equals(IdCostProducts.NAME_APPLE) && Double.parseDouble(product[2]) == IdCostProducts.COST_APPLE ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_MILK && product[1].equals(IdCostProducts.NAME_MILK) && Double.parseDouble(product[2]) == IdCostProducts.COST_MILK ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_MEAT && product[1].equals(IdCostProducts.NAME_MEAT) && Double.parseDouble(product[2]) == IdCostProducts.COST_MEAT)
                .map(product -> new Product(Integer.parseInt(product[3]), product[1], Double.parseDouble(product[2])));

        Stream<Product> promoProductStream = RegexData.validation(parameters).stream()
                .map(string -> string.split(";"))
                .filter(product -> Integer.parseInt(product[0]) == IdCostProducts.ID_EGGS && product[1].equals(IdCostProducts.NAME_EGGS) && Double.parseDouble(product[2]) == IdCostProducts.COST_EGGS ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_CHEESE && product[1].equals(IdCostProducts.NAME_CHEESE) && Double.parseDouble(product[2]) == IdCostProducts.COST_CHEESE ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_BREAD && product[1].equals(IdCostProducts.NAME_BREAD) && Double.parseDouble(product[2]) == IdCostProducts.COST_BREAD ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_FISH && product[1].equals(IdCostProducts.NAME_FISH) && Double.parseDouble(product[2]) == IdCostProducts.COST_FISH ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_OIL && product[1].equals(IdCostProducts.NAME_OIL) && Double.parseDouble(product[2]) == IdCostProducts.COST_OIL ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_CHOCOLATE && product[1].equals(IdCostProducts.NAME_CHOCOLATE) && Double.parseDouble(product[2]) == IdCostProducts.COST_CHOCOLATE)
                .map(product -> new PromotionsProduct(Integer.parseInt(product[3]), product[1], Double.parseDouble(product[2])));

        List<Product> products = Stream.concat(productStream, promoProductStream)
                .collect(Collectors.toList());

        return products;
    }

    public static double sum(List<Product> products) {

        Optional<Double> total = products.stream()
                .map(product -> product.getQua() * product.getCost())
                .reduce(Double::sum);
        return total.orElseThrow();
    }

    public static long promoProducts(List<Product> products) {
        long count = products.stream()
                .filter(product -> product instanceof PromotionsProduct)
                .count();

        return count;
    }

    public static int getDiscount(int numberCard) {
        int discount = 0;

        String bonus = DiscountCard.addCard().stream()
                .filter(discountCard -> discountCard.getNumber() == numberCard)
                .map(DiscountCard::getBonus)
                .findFirst()
                .orElse("");

        switch (bonus) {
            case "StandardCard" -> discount = 3;
            case "SilverCard" -> discount = 5;
            case "GoldCard" -> discount = 7;
        }

        return discount;
    }

    public static double getTotal(double sum, int discount, double promoDiscount) {
        sum -= sum * (discount / 100.0 + promoDiscount);
        sum = (double) Math.round(sum * 100) / 100;
        return sum;
    }
}