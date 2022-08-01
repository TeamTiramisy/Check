package com.console.check.service;

import com.console.check.dto.ProductReadDto;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.console.check.util.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PrintCheckService {

    private static final PrintCheckService INSTANCE = new PrintCheckService();

    private final CheckService service = CheckService.getInstance();

    @SneakyThrows
    public  void printHeader(FileOutputStream outputStream){
        outputStream.write(String.format("%-11s %s %10s\n", EMPTY, CHECK, EMPTY).getBytes());
        outputStream.write(String.format("%-5s %s %10s\n", EMPTY, SUPERMARKET, EMPTY).getBytes());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE);
        outputStream.write(String.format("%s %22s\n", TERMINAL, date.format(formatterDate)).getBytes());
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME);
        outputStream.write(String.format("%s %21s\n", NAME, time.format(formatterTime)).getBytes());
        outputStream.write(DASH.getBytes());

    }

    @SneakyThrows
    public void printCheck(List<ProductReadDto> products, FileOutputStream outputStream){
        outputStream.write(String.format("%-6s %-10s %6s %8s\n\n", AMOUNT, NAME_PRODUCT, PRICE, TOTAL).getBytes());
        double sum = service.sum(products);
        for (int i = 0; i < products.size(); i++) {
            double price = products.get(i).getQua() * products.get(i).getCost();
            outputStream.write(String.format("%-5d %-9s %7.2f %7.2f\n", products.get(i).getQua(), products.get(i).getName(), products.get(i).getCost(), price).getBytes());
        }
        outputStream.write(TRAIT.getBytes());
    }

    @SneakyThrows
    public void printTotal(List<ProductReadDto> products, FileOutputStream outputStream) {
        double total = service.sum(products);

        int discount = service.getDiscount(1);

        if (discount > 0) {


            if (service.promoProducts(products) > 5) {
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write(String.format("%-10s %16s\n", DISCOUNT, discount + "%").getBytes());
                outputStream.write(PROMO.getBytes());
                double totalDiscount = service.getTotal(total, discount, 0.1);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, totalDiscount).getBytes());

            } else {
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write(String.format("%-10s %16s\n", DISCOUNT, discount + "%").getBytes());
                double totalDiscount = service.getTotal(total, discount, 0.0);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, totalDiscount).getBytes());
            }

        } else {
            if (service.promoProducts(products) > 5) {
                outputStream.write(String.format("%-10s %19.2f\n", SUM, total).getBytes());
                outputStream.write(CARD_EMPTY.getBytes());
                outputStream.write(PROMO.getBytes());
                double totalDiscount = service.getTotal(total, 0, 0.1);
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL, totalDiscount).getBytes());
            } else {
                outputStream.write(CARD_EMPTY.getBytes());
                outputStream.write(String.format("%-10s %19.2f\n", TOTAL_PRICE, total).getBytes());
            }
        }
    }

    @SneakyThrows
    public void getCheck(String pathTXT, String pathPDF){
        try (PdfWriter writer = new PdfWriter(pathPDF);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {
            pdfDoc.setDefaultPageSize(new PageSize(300, 460));
            document.setTextAlignment(TextAlignment.CENTER);
            document.setWordSpacing(3.5f);
            String para1 = new String(Files.readAllBytes(Paths.get(pathTXT)));

            Paragraph paragraph1 = new Paragraph(para1);


            document.add(paragraph1);
        }
    }

    @SneakyThrows
    public void print(List<ProductReadDto> products, String pathTXT, String pathPDF){
        File file = Path.of(PATH_CHECK_TXT).toFile();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            printHeader(outputStream);

            printCheck(products, outputStream);

            printTotal(products, outputStream);

            getCheck(pathTXT, pathPDF);
        }
    }



    public static PrintCheckService getInstance() {
        return INSTANCE;
    }
}
