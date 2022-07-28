package com.console.check.service;

import com.console.check.entity.Product;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.console.check.constants.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PrintServiceDB {

    private static final PrintServiceDB INSTANCE = new PrintServiceDB();

    private final CheckServiceDB checkServiceDB = CheckServiceDB.getInstance();

    public  void printHeader() {
        System.out.printf("%-8s %s %10s", EMPTY, CHECK, EMPTY);
        System.out.println();
        System.out.printf("%-5s %s %10s", EMPTY, SUPERMARKET, EMPTY);
        System.out.println();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE);
        System.out.printf("%s %18s", TERMINAL, date.format(formatterDate));
        System.out.println();
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME);
        System.out.printf("%s %18s", NAME, time.format(formatterTime));
        System.out.println();
        System.out.print(DASH);

    }

    public void printCheck(List<Product> products){
        System.out.printf("%-3s %-10s %5s %7s", AMOUNT, NAME_PRODUCT, PRICE, TOTAL);
        System.out.println();
        System.out.println();
        double sum = checkServiceDB.sum(products);
        for (int i = 0; i < products.size(); i++) {
            double price = products.get(i).getQua() * products.get(i).getCost();
            System.out.printf("%-3d %-10s %7.2f %7.2f", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price);
            System.out.println();
        }
        System.out.print(TRAIT);
    }

    public void printTotal(List<Product> products){
        double total = checkServiceDB.sum(products);

        int discount = checkServiceDB.getDiscount(1);

        if (discount > 0) {

            if (checkServiceDB.promoProducts(products) > 5) {
                System.out.printf("%-10s %19.2f", SUM, total);
                System.out.println();
                System.out.println(DISCOUNT + discount + "%");
                System.out.print(PROMO);
                double totalDiscount = checkServiceDB.getTotal(total, discount, 0.1);
                System.out.printf("%-10s %19.2f", TOTAL_PRICE, totalDiscount);

            } else {
                System.out.printf("%-10s %19.2f", SUM, total);
                System.out.println();
                System.out.println(DISCOUNT + discount + "%");
                double totalDiscount = checkServiceDB.getTotal(total, discount, 0.0);
                System.out.printf("%-10s %19.2f", TOTAL_PRICE, totalDiscount);
            }

        } else {
            if (checkServiceDB.promoProducts(products) > 5) {
                System.out.printf("%-10s %19.2f", SUM, total);
                System.out.println();
                System.out.print(CARD_EMPTY);
                System.out.print(PROMO);
                double totalDiscount = checkServiceDB.getTotal(total, 0, 0.1);
                System.out.printf("%-10s %19.2f", TOTAL_PRICE, totalDiscount);
            } else {
                System.out.print(CARD_EMPTY);
                System.out.printf("%-10s %19.2f", TOTAL_PRICE, total);
            }
        }
    }

    public void printDB(){
            List<Product> list = checkServiceDB.addProducts();
            printHeader();
            printCheck(list);
            printTotal(list);
    }

    public static PrintServiceDB getInstance() {
        return INSTANCE;
    }
}
