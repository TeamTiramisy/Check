package com.console.check.service;


import com.console.check.entity.Promo;
import com.console.check.entity.Card;
import com.console.check.entity.Product;
import com.console.check.regex.RegexData;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.console.check.constants.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class CheckServiceFile implements CheckService {

    private static final CheckServiceFile INSTANCE = new CheckServiceFile();
    private final DiscountService discountService = DiscountService.getInstance();

    @SneakyThrows
    public List<Product> addProducts() {
        String path = "fileCheck/products.txt";
        String parameters = new String(Files.readAllBytes(Paths.get(path)));

        Stream<Product> productStream = RegexData.validation(parameters).stream()
                .map(string -> string.split(";"))
                .filter(product -> Integer.parseInt(product[0]) == ID_APPLE && product[1].equals(NAME_APPLE) && Double.parseDouble(product[2]) == COST_APPLE ||
                        Integer.parseInt(product[0]) == ID_MILK && product[1].equals(NAME_MILK) && Double.parseDouble(product[2]) == COST_MILK ||
                        Integer.parseInt(product[0]) == ID_MEAT && product[1].equals(NAME_MEAT) && Double.parseDouble(product[2]) == COST_MEAT)
                .map(product -> new Product(Integer.parseInt(product[0]), Integer.parseInt(product[3]), product[1], Double.parseDouble(product[2]), Promo.NO));

        Stream<Product> promoProductStream = RegexData.validation(parameters).stream()
                .map(string -> string.split(";"))
                .filter(product -> Integer.parseInt(product[0]) == ID_EGGS && product[1].equals(NAME_EGGS) && Double.parseDouble(product[2]) == COST_EGGS ||
                        Integer.parseInt(product[0]) == ID_CHEESE && product[1].equals(NAME_CHEESE) && Double.parseDouble(product[2]) == COST_CHEESE ||
                        Integer.parseInt(product[0]) == ID_BREAD && product[1].equals(NAME_BREAD) && Double.parseDouble(product[2]) == COST_BREAD ||
                        Integer.parseInt(product[0]) == ID_FISH && product[1].equals(NAME_FISH) && Double.parseDouble(product[2]) == COST_FISH ||
                        Integer.parseInt(product[0]) == ID_OIL && product[1].equals(NAME_OIL) && Double.parseDouble(product[2]) == COST_OIL ||
                        Integer.parseInt(product[0]) == ID_CHOCOLATE && product[1].equals(NAME_CHOCOLATE) && Double.parseDouble(product[2]) == COST_CHOCOLATE)
                .map(product -> new Product(Integer.parseInt(product[0]), Integer.parseInt(product[3]), product[1], Double.parseDouble(product[2]), Promo.YES));

        List<Product> products = Stream.concat(productStream, promoProductStream)
                .collect(Collectors.toList());

        return products;
    }

    public double sum(List<Product> products) {

        Optional<Double> total = products.stream()
                .map(product -> product.getQua() * product.getCost())
                .reduce(Double::sum);
        return total.orElseThrow();
    }

    public int promoProducts(List<Product> products) {
        int count = (int) products.stream()
                .filter(product -> product.getPromo().equals(Promo.YES))
                .count();

        return count;
    }

    public int getDiscount(Integer id) {
        int discount = 0;

        String bonus = discountService.addCard().stream()
                .filter(card -> card.getId().equals(id))
                .map(Card::getBonus)
                .findFirst()
                .orElse("");

        switch (bonus) {
            case STANDARD -> discount = 3;
            case SILVER -> discount = 5;
            case GOLD -> discount = 7;
        }

        return discount;
    }

    public double getTotal(double sum, int discount, double promoDiscount) {
        sum -= sum * (discount / 100.0 + promoDiscount);
        sum = (double) Math.round(sum * 100) / 100;
        return sum;
    }

    public static CheckService getProxy() {
        Gson gson = new Gson();
        return (CheckService) Proxy.newProxyInstance(INSTANCE.getClass().getClassLoader(), INSTANCE.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    log.info("метод: " + method.getName());
                    log.info("параметры: " + Arrays.toString(method.getParameterTypes()) +
                            " " + gson.toJson(args));
                    log.info("возвращаемое значение: " + method.getReturnType() +
                            " " + gson.toJson(method.invoke(INSTANCE, args)) + "\n");
                    return method.invoke(INSTANCE, args);
                });
    }

    public static CheckServiceFile getInstance() {
        return INSTANCE;
    }

}