package com.console.check.service;

import com.console.check.entity.DiscountCard;
import com.console.check.entity.Product;
import lombok.NoArgsConstructor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.console.check.constants.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PrintService {

    private static final PrintService INSTANCE = new PrintService();

    private final CheckService checkService = CheckServiceImpl.getProxy();
    private final DiscountService discountService = DiscountService.getInstance();

    public void printStart() {
        System.out.println(START_LINE);
    }

    public  void printHeader(FileOutputStream outputStream) throws IOException {
        System.out.printf("%-8s %s %10s", EMPTY, CHECK, EMPTY);
        System.out.println();
        System.out.printf("%-5s %s %10s", EMPTY, SUPERMARKET, EMPTY);
        System.out.println();
        outputStream.write(String.format("%-8s %s %10s\n", EMPTY, CHECK, EMPTY).getBytes());
        outputStream.write(String.format("%-5s %s %10s\n", EMPTY, CHECK, EMPTY).getBytes());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE);
        System.out.printf("%s %18s", TERMINAL, date.format(formatterDate));
        System.out.println();
        outputStream.write(String.format("%s %18s\n", TERMINAL, date.format(formatterDate)).getBytes());
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME);
        System.out.printf("%s %18s", NAME, time.format(formatterTime));
        System.out.println();
        System.out.print(DASH);
        outputStream.write(String.format("%s %18s\n", NAME, time.format(formatterTime)).getBytes());
        outputStream.write(DASH.getBytes());

    }

    public void printCheck(List<Product> products, FileOutputStream outputStream) throws IOException{
        System.out.printf("%-3s %-10s %5s %7s", AMOUNT, NAME_PRODUCT, PRICE, TOTAL);
        System.out.println();
        System.out.println();
        outputStream.write(String.format("%-3s %-10s %5s %7s\n\n", AMOUNT, NAME_PRODUCT, PRICE, TOTAL).getBytes());
        double sum = checkService.sum(products);
        for (int i = 0; i < products.size(); i++) {
            double price = products.get(i).getQua() * products.get(i).getCost();
            System.out.printf("%-3d %-10s %7.2f %7.2f", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price);
            System.out.println();
            outputStream.write(String.format("%-3d %-10s %7.2f %7.2f\n", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price).getBytes());
        }
        System.out.print(TRAIT);
        outputStream.write(TRAIT.getBytes());
    }

    public void printTotal(int numberCard, List<Product> products, FileOutputStream outputStream) throws IOException {
        double total = checkService.sum(products);
        List<DiscountCard> listCards = discountService.addCard();

        if (numberCard > 0 && numberCard < 11) {

            int discount = checkService.getDiscount(numberCard);

            if (checkService.promoProducts(products) > 5) {
                System.out.printf("%-10s %19.2f", SUM, total);
                System.out.println();
                System.out.println(DISCOUNT + discount + "%");
                System.out.print(PROMO);
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write((DISCOUNT + discount + "%\n").getBytes());
                outputStream.write(PROMO.getBytes());
                double totalDiscount = checkService.getTotal(total, discount, 0.1);
                System.out.printf("%-10s %19.2f", TOTAL_PRICE, totalDiscount);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, totalDiscount).getBytes());

            } else {
                System.out.printf("%-10s %19.2f", SUM, total);
                System.out.println();
                System.out.println(DISCOUNT + discount + "%");
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write((DISCOUNT + discount + "%\n").getBytes());
                double totalDiscount = checkService.getTotal(total, discount, 0.0);
                System.out.printf("%-10s %19.2f", TOTAL_PRICE, totalDiscount);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, totalDiscount).getBytes());
            }

        } else {
            if (checkService.promoProducts(products) > 5) {
                System.out.printf("%-10s %19.2f", SUM, total);
                System.out.println();
                System.out.print(CARD_EMPTY);
                System.out.print(PROMO);
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write(CARD_EMPTY.getBytes());
                outputStream.write(PROMO.getBytes());
                double totalDiscount = checkService.getTotal(total, 0, 0.1);
                System.out.printf("%-10s %19.2f", TOTAL_PRICE, totalDiscount);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL, totalDiscount).getBytes());
            } else {
                System.out.print(CARD_EMPTY);
                System.out.printf("%-10s %19.2f", TOTAL_PRICE, total);
                outputStream.write(CARD_EMPTY.getBytes());
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, total).getBytes());
            }
        }
    }

    public static PrintService getInstance() {
        return INSTANCE;
    }
}
