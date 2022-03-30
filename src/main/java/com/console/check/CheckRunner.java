package com.console.check;

import com.console.check.collection.CustomArrayList;
import com.console.check.collection.CustomList;
import com.console.check.products.*;
import com.console.check.regex.RegexData;
import lombok.SneakyThrows;

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

    @SneakyThrows
    public static void printFileHeader(FileOutputStream outputStream) {
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

    @SneakyThrows
    public static CustomList<Product> addProducts() {
        CustomList<Product> products = new CustomArrayList<>();
        String file = "fileCheck/products.txt";
        String parameters = new String(Files.readAllBytes(Paths.get(file)));
        CustomList<String> product = RegexData.validation(parameters);
        for (int i = 0; i < product.size(); i++) {
            String[] idNameCostQua = product.get(i).split(";");
            int productId = Integer.parseInt(idNameCostQua[0]);
            String productName = idNameCostQua[1];
            double productCost = Double.parseDouble(idNameCostQua[2]);
            int productQua = Integer.parseInt(idNameCostQua[3]);

            if (productId == IdCostProducts.ID_APPLE && productName.equals(IdCostProducts.NAME_APPLE) && productCost == IdCostProducts.COST_APPLE){
                products.add(new Product(productQua, productName, productCost));
            } else if (productId == IdCostProducts.ID_MILK && productName.equals(IdCostProducts.NAME_MILK) && productCost == IdCostProducts.COST_MILK){
                products.add(new Product(productQua, productName, productCost));
            } else if (productId == IdCostProducts.ID_MEAT && productName.equals(IdCostProducts.NAME_MEAT) && productCost == IdCostProducts.COST_MEAT){
                products.add(new Product(productQua, productName, productCost));
            } else if (productId == IdCostProducts.ID_EGGS && productName.equals(IdCostProducts.NAME_EGGS) && productCost == IdCostProducts.COST_EGGS){
                products.add(new PromotionsProduct(productQua, productName, productCost));
            } else if (productId == IdCostProducts.ID_CHEESE && productName.equals(IdCostProducts.NAME_CHEESE) && productCost == IdCostProducts.COST_CHEESE){
                products.add(new PromotionsProduct(productQua, productName, productCost));
            } else if (productId == IdCostProducts.ID_BREAD && productName.equals(IdCostProducts.NAME_BREAD) && productCost == IdCostProducts.COST_BREAD){
                products.add(new PromotionsProduct(productQua, productName, productCost));
            } else if (productId == IdCostProducts.ID_FISH && productName.equals(IdCostProducts.NAME_FISH) && productCost == IdCostProducts.COST_FISH){
                products.add(new PromotionsProduct(productQua, productName, productCost));
            } else if (productId == IdCostProducts.ID_OIL && productName.equals(IdCostProducts.NAME_OIL) && productCost == IdCostProducts.COST_OIL){
                products.add(new PromotionsProduct(productQua, productName, productCost));
            } else if (productId == IdCostProducts.ID_CHOCOLATE && productName.equals(IdCostProducts.NAME_CHOCOLATE) && productCost == IdCostProducts.COST_CHOCOLATE){
                products.add(new PromotionsProduct(productQua, productName, productCost));
            } else {
                throw new WrongIdException("Неверный id, имя или цена товара");
            }
        }
        return products;
    }

    public static double printCheck(CustomList<Product> products) {
        System.out.printf("%-3s %-10s %5s %7s", "кол", "наименование", "цена", "итог");
        System.out.println();
        System.out.println();
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            double price = products.get(i).getQua() * products.get(i).getCost();
            total += price;
            System.out.printf("%-3d %-10s %7.2f %7.2f", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price);
            System.out.println();
        }
        System.out.println("==============================");
        return total;
    }

    @SneakyThrows
    public static void printFileCheck(CustomList<Product> products, FileOutputStream outputStream) {
        outputStream.write(String.format("%-3s %-10s %5s %7s\n\n", "кол", "наименование", "цена", "итог").getBytes());
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            double price = products.get(i).getQua() * products.get(i).getCost();
            total += price;
            outputStream.write(String.format("%-3d %-10s %7.2f %7.2f\n", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price).getBytes());
        }
        outputStream.write("==============================\n".getBytes());
    }

    public static void printTotal(double total, int numberCard, CustomList<Product> products) {
        CustomList<DiscountCard> listCards = DiscountCard.addCard();
        int discProduct = 0;
        double discount = 0;
        int promoProducts = 0;
        if (numberCard > 0 && numberCard < 11) {
            for (int i = 0; i < listCards.size(); i++) {
                if (listCards.get(i).getNumber() == numberCard) {
                    switch (listCards.get(i).getBonus()) {
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
                }
            }
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i) instanceof PromotionsProduct) {
                    promoProducts++;
                }
            }
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
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i) instanceof PromotionsProduct) {
                    promoProducts++;
                }
            }
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

    @SneakyThrows
    public static void printFileTotal(double total, int numberCard, CustomList<Product> products, FileOutputStream outputStream) {
        CustomList<DiscountCard> listCards = DiscountCard.addCard();
        int discProduct = 0;
        double discount = 0;
        int promoProducts = 0;
        if (numberCard > 0 && numberCard < 11) {
            for (int i = 0; i < listCards.size(); i++) {
                if (listCards.get(i).getNumber() == numberCard) {
                    switch (listCards.get(i).getBonus()) {
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
                }
            }
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i) instanceof PromotionsProduct) {
                    promoProducts++;
                }
            }
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
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i) instanceof PromotionsProduct) {
                    promoProducts++;
                }
            }
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

    @SneakyThrows
    public static void main(String[] args) {
        printStart();
        File file = Path.of("fileCheck/check.txt").toFile();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileOutputStream outputStream = new FileOutputStream(file)) {
            int numberCard = Integer.parseInt(reader.readLine());

                CustomList<Product> products = addProducts();

                printHeader();
                printFileHeader(outputStream);

                double total = printCheck(products);
                printFileCheck(products, outputStream);

                printTotal(total, numberCard, products);
                printFileTotal(total, numberCard, products, outputStream);
        }
    }
}
