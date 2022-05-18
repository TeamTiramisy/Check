package com.console.check;

import com.console.check.products.*;
import com.console.check.regex.RegexData;
import com.console.check.service.CheckService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckRunner {

    public static void printStart() {
        System.out.println("Предоставте дисконтную карту:");
    }

    public static void printHeader(FileOutputStream outputStream) throws IOException{
        System.out.printf("%-8s %s %10s", " ", "ЧЕК ПРОДАЖИ", " ");
        System.out.println();
        System.out.printf("%-5s %s %10s", " ", "Супермаркет: АЛМИ", " ");
        System.out.println();
        outputStream.write(String.format("%-8s %s %10s\n", " ", "ЧЕК ПРОДАЖИ", " ").getBytes());
        outputStream.write(String.format("%-5s %s %10s\n", " ", "Супермаркет: АЛМИ", " ").getBytes());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("%s %18s", "Терминал №6", date.format(formatterDate));
        System.out.println();
        outputStream.write(String.format("%s %18s\n", "Терминал №6", date.format(formatterDate)).getBytes());
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.printf("%s %18s", "Иван Иванов", time.format(formatterTime));
        System.out.println();
        System.out.println("------------------------------");
        outputStream.write(String.format("%s %18s\n", "Иван Иванов", time.format(formatterTime)).getBytes());
        outputStream.write("------------------------------\n".getBytes());

    }

    public static void printCheck(List<Product> products, FileOutputStream outputStream) throws IOException{
        System.out.printf("%-3s %-10s %5s %7s", "кол", "наименование", "цена", "итог");
        System.out.println();
        System.out.println();
        outputStream.write(String.format("%-3s %-10s %5s %7s\n\n", "кол", "наименование", "цена", "итог").getBytes());
        double sum = CheckService.sum(products);
        for (int i = 0; i < products.size(); i++) {
            double price = products.get(i).getQua() * products.get(i).getCost();
            System.out.printf("%-3d %-10s %7.2f %7.2f", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price);
            System.out.println();
            outputStream.write(String.format("%-3d %-10s %7.2f %7.2f\n", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price).getBytes());
        }
        System.out.println("==============================");
        outputStream.write("==============================\n".getBytes());
    }

    public static void printTotal(int numberCard, List<Product> products, FileOutputStream outputStream) throws IOException {
        double total = CheckService.sum(products);
        List<DiscountCard> listCards = DiscountCard.addCard();

        if (numberCard > 0 && numberCard < 11) {

            int discount = CheckService.getDiscount(numberCard);

            if (CheckService.promoProducts(products) > 5) {
                System.out.printf("%-10s %19.2f", "СУММА", total);
                System.out.println();
                System.out.println("Скидка по карте " + discount + "%");
                System.out.println("Скидка по акционным товарам 10%");
                outputStream.write(String.format("%-10s %19.2f\n", "СУММА", total).getBytes());
                outputStream.write(("Скидка по карте " + discount + "%\n").getBytes());
                outputStream.write("Скидка по акционным товарам 10%\n".getBytes());
                double totalDiscount = CheckService.getTotal(total, discount, 0.1);
                System.out.printf("%-10s %19.2f", "ИТОГ", totalDiscount);
                outputStream.write(String.format("%-10s %19.2f\n", "ИТОГ", totalDiscount).getBytes());

            } else {
                System.out.printf("%-10s %19.2f", "СУММА", total);
                System.out.println();
                System.out.println("Скидка по карте " + discount + "%");
                outputStream.write(String.format("%-10s %19.2f\n", "СУММА", total).getBytes());
                outputStream.write(("Скидка по карте " + discount + "%\n").getBytes());
                double totalDiscount = CheckService.getTotal(total, discount, 0.0);
                System.out.printf("%-10s %19.2f", "ИТОГ", totalDiscount);
                outputStream.write(String.format("%-10s %19.2f\n", "ИТОГ", totalDiscount).getBytes());
            }

        } else {
            if (CheckService.promoProducts(products) > 5) {
                System.out.printf("%-10s %19.2f", "СУММА", total);
                System.out.println();
                System.out.println("Скидочная карта не предьявлена");
                System.out.println("Скидка по акционным товарам 10%");
                outputStream.write(String.format("%-10s %19.2f\n", "СУММА", total).getBytes());
                outputStream.write("Скидочная карта не предьявлена\n".getBytes());
                outputStream.write("Скидка по акционным товарам 10%\n".getBytes());
                double totalDiscount = CheckService.getTotal(total, 0, 0.1);
                System.out.printf("%-10s %19.2f", "ИТОГ", totalDiscount);
                outputStream.write(String.format("%-10s %19.2f\n", "ИТОГ", totalDiscount).getBytes());
            } else {
                System.out.println("Скидочная карта не предьявлена");
                System.out.printf("%-10s %19.2f", "ИТОГ", total);
                outputStream.write("Скидочная карта не предьявлена\n".getBytes());
                outputStream.write(String.format("%-10s %19.2f\n", "ИТОГ", total).getBytes());
            }
        }
    }


    public static void main(String[] args) throws IOException {
        printStart();
        File file = Path.of("fileCheck/check.txt").toFile();
        String path = "fileCheck/products.txt";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileOutputStream outputStream = new FileOutputStream(file)) {
            int numberCard = Integer.parseInt(reader.readLine());

            List<Product> products = CheckService.addProducts(path);

            printHeader(outputStream);

            printCheck(products, outputStream);

            printTotal(numberCard, products, outputStream);
        }
    }
}