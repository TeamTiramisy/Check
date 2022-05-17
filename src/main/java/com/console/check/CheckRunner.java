package com.console.check;

import com.console.check.products.*;
import com.console.check.regex.RegexData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckRunner {

    public static void printStart() {
        System.out.println("Предоставте дисконтную карту:");
    }

    public static void printHeader() {
        System.out.printf("%-8s %s %10s", " ", "ЧЕК ПРОДАЖИ", " ");
        System.out.println();
        System.out.printf("%-5s %s %10s", " ", "Супермаркет: АЛМИ", " ");
        System.out.println();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("%s %18s", "Терминал №6", date.format(formatterDate));
        System.out.println();
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.printf("%s %18s", "Иван Иванов", time.format(formatterTime));
        System.out.println();
        System.out.println("------------------------------");

    }

    public static void printFileHeader(FileOutputStream outputStream) throws IOException {
        outputStream.write(String.format("%-8s %s %10s\n", " ", "ЧЕК ПРОДАЖИ", " ").getBytes());
        outputStream.write(String.format("%-5s %s %10s\n", " ", "Супермаркет: АЛМИ", " ").getBytes());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        outputStream.write(String.format("%s %18s\n", "Терминал №6", date.format(formatterDate)).getBytes());
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        outputStream.write(String.format("%s %18s\n", "Иван Иванов", time.format(formatterTime)).getBytes());
        outputStream.write("------------------------------\n".getBytes());

    }

    public static List<Product> addProducts() throws WrongIdException, IOException {
        String file = "fileCheck/products.txt";
        String parameters = new String(Files.readAllBytes(Paths.get(file)));

        Stream<Product> productStream = RegexData.validation(parameters).stream()
                .map(string -> string.split(";"))
                .filter(product -> Integer.parseInt(product[0]) == IdCostProducts.ID_APPLE && product[1].equals(IdCostProducts.NAME_APPLE) && Double.parseDouble(product[2]) == IdCostProducts.COST_APPLE ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_MILK && product[1].equals(IdCostProducts.NAME_MILK) && Double.parseDouble(product[2]) == IdCostProducts.COST_MILK ||
                        Integer.parseInt(product[0]) == IdCostProducts.ID_MEAT && product[1].equals(IdCostProducts.NAME_MEAT) && Double.parseDouble(product[2]) == IdCostProducts.COST_MEAT )
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

    public static double printCheck(List<Product> products) {
        System.out.printf("%-3s %-10s %5s %7s", "кол", "наименование", "цена", "итог");
        System.out.println();
        System.out.println();

        Optional<Double> total = products.stream()
                .peek(product -> System.out.printf("%-3d %-10s %7.2f %7.2f\n", product.getQua(), product.getName(), product.getCost(), product.getQua() * product.getCost()))
                .map(product -> product.getQua() * product.getCost())
                .reduce(Double::sum);

        System.out.println("==============================");
        return total.get();
    }

    public static void printFileCheck(List<Product> products, FileOutputStream outputStream) throws IOException {
        outputStream.write(String.format("%-3s %-10s %5s %7s\n\n", "кол", "наименование", "цена", "итог").getBytes());

        Optional<Double> total = products.stream()
                .peek(product -> {
                    try {
                        outputStream.write(String.format("%-3d %-10s %7.2f %7.2f\n", product.getQua(), product.getName(), product.getCost(), product.getQua() * product.getCost()).getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .map(product -> product.getQua() * product.getCost())
                .reduce(Double::sum);

        outputStream.write("==============================\n".getBytes());
    }

    public static void printTotal(double total, int numberCard, List<Product> products) {
        int discProduct = 0;
        double discount = 0;
        if (numberCard > 0 && numberCard < 11) {
            String bonus = DiscountCard.addCard().stream()
                    .filter(card -> card.getNumber() == numberCard)
                    .map(DiscountCard::getBonus)
                    .findFirst()
                    .get();

            switch (bonus) {
                case "StandardCard":
                    discProduct = 3;
                    discount = 0.03;
                    break;
                case "SilverCard":
                    discProduct = 5;
                    discount = 0.05;
                    break;
                case "GoldCard":
                    discProduct = 7;
                    discount = 0.07;
                    break;
            }

            long promoProducts = products.stream()
                    .map(product -> product instanceof PromotionsProduct)
                    .count();

            if (promoProducts > 5) {
                System.out.printf("%-10s %19.2f", "СУММА", total);
                System.out.println();
                System.out.println("Скидка по карте " + discProduct + "%");
                System.out.println("Скидка по акционным товарам 10%");
                total -= total * (discount + 0.1);
                System.out.printf("%-10s %19.2f", "ИТОГ", total);
            } else {
                System.out.printf("%-10s %19.2f", "СУММА", total);
                System.out.println();
                System.out.println("Скидка по карте " + discProduct + "%");
                total -= total * discount;
                System.out.printf("%-10s %19.2f", "ИТОГ", total);
            }

        } else {
            long promoProducts = products.stream()
                    .map(product -> product instanceof PromotionsProduct)
                    .count();

            if (promoProducts > 5) {
                System.out.printf("%-10s %19.2f", "СУММА", total);
                System.out.println();
                System.out.println("Скидочная карта не предьявлена");
                System.out.println("Скидка по акционным товарам 10%");
                total -= total * 0.1;
                System.out.printf("%-10s %19.2f", "ИТОГ", total);
            } else {
                System.out.println("Скидочная карта не предьявлена");
                System.out.printf("%-10s %19.2f", "ИТОГ", total);
            }
        }
    }

    public static void printFileTotal(double total, int numberCard, List<Product> products, FileOutputStream outputStream) throws IOException {
        int discProduct = 0;
        double discount = 0;
        if (numberCard > 0 && numberCard < 11) {
            String bonus = DiscountCard.addCard().stream()
                    .filter(card -> card.getNumber() == numberCard)
                    .map(DiscountCard::getBonus)
                    .findFirst()
                    .get();

            switch (bonus) {
                case "StandardCard":
                    discProduct = 3;
                    discount = 0.03;
                    break;
                case "SilverCard":
                    discProduct = 5;
                    discount = 0.05;
                    break;
                case "GoldCard":
                    discProduct = 7;
                    discount = 0.07;
                    break;
            }

            long promoProducts = products.stream()
                    .map(product -> product instanceof PromotionsProduct)
                    .count();

            if (promoProducts > 5) {
                outputStream.write(String.format("%-10s %19.2f\n", "СУММА", total).getBytes());
                outputStream.write(("Скидка по карте " + discProduct + "%\n").getBytes());
                outputStream.write("Скидка по акционным товарам 10%\n".getBytes());
                total -= total * (discount + 0.1);
                outputStream.write(String.format("%-10s %19.2f\n", "ИТОГ", total).getBytes());
            } else {
                outputStream.write(String.format("%-10s %19.2f\n", "СУММА", total).getBytes());
                outputStream.write(("Скидка по карте " + discProduct + "%\n").getBytes());
                total -= total * discount;
                outputStream.write(String.format("%-10s %19.2f\n", "ИТОГ", total).getBytes());
            }

        } else {
            long promoProducts = products.stream()
                    .map(product -> product instanceof PromotionsProduct)
                    .count();

            if (promoProducts > 5) {
                outputStream.write(String.format("%-10s %19.2f\n", "СУММА", total).getBytes());
                outputStream.write("Скидочная карта не предьявлена\n".getBytes());
                outputStream.write("Скидка по акционным товарам 10%\n".getBytes());
                total -= total * 0.1;
                outputStream.write(String.format("%-10s %19.2f\n", "ИТОГ", total).getBytes());
            } else {
                outputStream.write("Скидочная карта не предьявлена\n".getBytes());
                outputStream.write(String.format("%-10s %19.2f\n", "ИТОГ", total).getBytes());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        printStart();
        File file = Path.of("fileCheck/check.txt").toFile();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileOutputStream outputStream = new FileOutputStream(file)) {
            int numberCard = Integer.parseInt(reader.readLine());
            try {
                List<Product> products = addProducts();

                printHeader();
                printFileHeader(outputStream);

                double total = printCheck(products);
                printFileCheck(products, outputStream);

                printTotal(total, numberCard, products);
                printFileTotal(total, numberCard, products, outputStream);
            } catch (WrongIdException e) {
                e.printStackTrace();
            }
        }
    }
}
