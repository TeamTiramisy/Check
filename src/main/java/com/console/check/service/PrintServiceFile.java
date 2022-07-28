package com.console.check.service;

import com.console.check.entity.Product;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.console.check.constants.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PrintServiceFile {

    private static final PrintServiceFile INSTANCE = new PrintServiceFile();

    private final CheckService checkService = CheckServiceFile.getProxy();

    public void printStart() {
        System.out.println(START_LINE);
    }

    @SneakyThrows
    public  void printHeader(FileOutputStream outputStream){
        outputStream.write(String.format("%-8s %s %10s\n", EMPTY, CHECK, EMPTY).getBytes());
        outputStream.write(String.format("%-5s %s %10s\n", EMPTY, CHECK, EMPTY).getBytes());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE);
        outputStream.write(String.format("%s %18s\n", TERMINAL, date.format(formatterDate)).getBytes());
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME);
        outputStream.write(String.format("%s %18s\n", NAME, time.format(formatterTime)).getBytes());
        outputStream.write(DASH.getBytes());

    }

    @SneakyThrows
    public void printCheck(List<Product> products, FileOutputStream outputStream){
        outputStream.write(String.format("%-3s %-10s %5s %7s\n\n", AMOUNT, NAME_PRODUCT, PRICE, TOTAL).getBytes());
        double sum = checkService.sum(products);
        for (int i = 0; i < products.size(); i++) {
            double price = products.get(i).getQua() * products.get(i).getCost();
            outputStream.write(String.format("%-3d %-10s %7.2f %7.2f\n", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price).getBytes());
        }
        outputStream.write(TRAIT.getBytes());
    }

    @SneakyThrows
    public void printTotal(int numberCard, List<Product> products, FileOutputStream outputStream){
        double total = checkService.sum(products);

        if (numberCard > 0 && numberCard < 11) {

            int discount = checkService.getDiscount(numberCard);

            if (checkService.promoProducts(products) > 5) {
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write((DISCOUNT + discount + "%\n").getBytes());
                outputStream.write(PROMO.getBytes());
                double totalDiscount = checkService.getTotal(total, discount, 0.1);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, totalDiscount).getBytes());

            } else {
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write((DISCOUNT + discount + "%\n").getBytes());
                double totalDiscount = checkService.getTotal(total, discount, 0.0);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, totalDiscount).getBytes());
            }

        } else {
            if (checkService.promoProducts(products) > 5) {
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write(CARD_EMPTY.getBytes());
                outputStream.write(PROMO.getBytes());
                double totalDiscount = checkService.getTotal(total, 0, 0.1);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL, totalDiscount).getBytes());
            } else {
                outputStream.write(CARD_EMPTY.getBytes());
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, total).getBytes());
            }
        }
    }

    @SneakyThrows
    public void printFile(){
        printStart();
        File file = Path.of("fileCheck/check.txt").toFile();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileOutputStream outputStream = new FileOutputStream(file)) {
            int numberCard = Integer.parseInt(reader.readLine());

            List<Product> products = checkService.addProducts();

            printHeader(outputStream);

            printCheck(products, outputStream);

            printTotal(numberCard, products, outputStream);
        }
    }

    public static PrintServiceFile getInstance() {
        return INSTANCE;
    }
}
