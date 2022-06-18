package com.console.check;

import com.console.check.entity.*;
import com.console.check.service.CheckService;
import com.console.check.service.CheckServiceImpl;
import com.console.check.service.PrintService;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckRunner {

    private static final CheckService checkService = CheckServiceImpl.getProxy();
    private static final PrintService printService = PrintService.getInstance();



    public static void main(String[] args) throws IOException {
        printService.printStart();
        File file = Path.of("fileCheck/check.txt").toFile();
        String path = "fileCheck/products.txt";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileOutputStream outputStream = new FileOutputStream(file)) {
            int numberCard = Integer.parseInt(reader.readLine());

            List<Product> products = checkService.addProducts(path);

            printService.printHeader(outputStream);

            printService.printCheck(products, outputStream);

            printService.printTotal(numberCard, products, outputStream);

        }
    }
}