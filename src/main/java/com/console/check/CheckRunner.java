package com.console.check;

import com.console.check.products.*;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckRunner {

    public static void printStart(String itemIdQuantity, int numberCard) {
        System.out.println("java CheckRunner " + itemIdQuantity + " card-" + numberCard);
        System.out.println();
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

    public static List<Product> addProducts(String itemIdQuantity) throws WrongIdException {
        List<Product> products = new ArrayList<>();
        String[] productsIdQua = itemIdQuantity.split(" ");
        for (int i = 0; i < productsIdQua.length; i++) {
            String[] idQua = productsIdQua[i].split("-");
            int itemId = Integer.parseInt(idQua[0]);
            int quantity = Integer.parseInt(idQua[1]);

            switch (itemId) {
                case IdCostProducts.ID_APPLE:
                    products.add(new Apple(quantity, IdCostProducts.NAME_APPLE, IdCostProducts.COST_APPLE));
                    break;
                case IdCostProducts.ID_MILK:
                    products.add(new Milk(quantity, IdCostProducts.NAME_MILK, IdCostProducts.COST_MILK));
                    break;
                case IdCostProducts.ID_MEAT:
                    products.add(new Meat(quantity, IdCostProducts.NAME_MEAT, IdCostProducts.COST_MEAT));
                    break;
                case IdCostProducts.ID_EGGS:
                    products.add(new Eggs(quantity, IdCostProducts.NAME_EGGS, IdCostProducts.COST_EGGS));
                    break;
                case IdCostProducts.ID_CHEESE:
                    products.add(new Cheese(quantity, IdCostProducts.NAME_CHEESE, IdCostProducts.COST_CHEESE));
                    break;
                case IdCostProducts.ID_BREAD:
                    products.add(new Bread(quantity, IdCostProducts.NAME_BREAD, IdCostProducts.COST_BREAD));
                    break;
                case IdCostProducts.ID_FISH:
                    products.add(new Fish(quantity, IdCostProducts.NAME_FISH, IdCostProducts.COST_FISH));
                    break;
                case IdCostProducts.ID_OIL:
                    products.add(new Oil(quantity, IdCostProducts.NAME_OIL, IdCostProducts.COST_OIL));
                    break;
                case IdCostProducts.ID_CHOCOLATE:
                    products.add(new Chocolate(quantity, IdCostProducts.NAME_CHOCOLATE, IdCostProducts.COST_CHOCOLATE));
                    break;
                default:
                    throw new WrongIdException();
            }
        }
        return products;
    }

    public static double printCheck(List<Product> products) {
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

    public static void printFileCheck(List<Product> products, FileOutputStream outputStream) throws IOException {
        outputStream.write(String.format("%-3s %-10s %5s %7s\n\n", "кол", "наименование", "цена", "итог").getBytes());
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            double price = products.get(i).getQua() * products.get(i).getCost();
            total += price;
            outputStream.write(String.format("%-3d %-10s %7.2f %7.2f\n", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price).getBytes());
        }
        outputStream.write("==============================\n".getBytes());
    }

    public static void printTotal(double total, int numberCard, List<Product> products) {
        List<DiscountCard> listCards = DiscountCard.addCard();
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

    public static void printFileTotal(double total, int numberCard, List<Product> products, FileOutputStream outputStream) throws IOException {
        List<DiscountCard> listCards = DiscountCard.addCard();
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

    public static void main(String[] args) throws IOException {
        File file = Path.of("fileCheck", "check.txt").toFile();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileOutputStream outputStream = new FileOutputStream(file)) {
            /* Ввести id и количество товара. Пример: 1-3 3-5 2-3*/
            String itemIdQuantity = reader.readLine();
            /* Ввести номер карты от 1 до 10*/
            int numberCard = Integer.parseInt(reader.readLine());

            printStart(itemIdQuantity, numberCard);
            try {
                List<Product> products = addProducts(itemIdQuantity);

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
