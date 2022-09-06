package com.console.check.service;

import com.console.check.dto.CheckDto;
import com.console.check.dto.ProductReadDto;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;


import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.itextpdf.layout.properties.TextAlignment;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.console.check.util.Constants.*;
@Service
@RequiredArgsConstructor
public class PrintCheckService {

    private final CheckService service;

    private final CheckService checkService;


    @SneakyThrows
    public void printCheck(CheckDto checkDto, OutputStream outputStream) {
        List<ProductReadDto> products = checkService.findAllById(checkDto.getId(), checkDto.getQua());
        Integer id = checkDto.getCard() != null ? checkDto.getCard() : ID_NOT_BONUS;
        int discount = service.getDiscount(id);
        double sum = service.sum(products);
        int promoProducts = service.promoProducts(products);

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {
            pdfDoc.setDefaultPageSize(new PageSize(PDF_WIDTH, PDF_HEIGHT));

            float[] columnWidth = {TABLE_WIDTH};
            Table table = new Table(columnWidth);

            table.addCell(setTextCenter(CHECK));
            table.addCell(setTextCenter(SUPERMARKET));

            float[] columnWidth2 = {TABLE_WIDTH / 2, TABLE_WIDTH / 2};
            Table table2 = new Table(columnWidth2);

            LocalDate date = LocalDate.now();
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE);
            LocalTime time = LocalTime.now();
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME);
            table2.addCell(setTextLeft(TERMINAL));
            table2.addCell(setTextRight(date.format(formatterDate)));
            table2.addCell(setTextLeft(NAME));
            table2.addCell(setTextRight(time.format(formatterTime)));

            Table table3 = new Table(columnWidth);
            table3.addCell(setTextCenter(TRAIT));

            float[] columnWidth3 = {TABLE_WIDTH / 4, TABLE_WIDTH / 4, TABLE_WIDTH / 4, TABLE_WIDTH / 4};
            Table table4 = new Table(columnWidth3);
            table4.addCell(setTextLeft(AMOUNT));
            table4.addCell(setTextLeft(NAME_PRODUCT));
            table4.addCell(setTextRight(PRICE));
            table4.addCell(setTextRight(TOTAL));


            for (int i = 0; i < products.size(); i++) {

                table4.addCell(setTextLeft(products.get(i).getQua().toString()));
                table4.addCell(setTextLeft(products.get(i).getName()));
                table4.addCell(setTextRight(products.get(i).getCost().toString()));
                table4.addCell(setTextRight(String.valueOf(products.get(i).getQua() * products.get(i).getCost())));
            }

            Table table5 = new Table(columnWidth);
            table5.addCell(setTextCenter(DASH));

            Table table6 = new Table(columnWidth2);
            table6.addCell(setTextLeft(SUM));
            table6.addCell(setTextRight(String.valueOf(sum)));

            document.add(table);
            document.add(table2);
            document.add(table3);
            document.add(table4);
            document.add(table5);
            document.add(table6);

            if (discount > 0){

                if (promoProducts > 5){

                    Table table7 = new Table(columnWidth);
                    table7.addCell(setTextLeft(DISCOUNT + discount + "%"));

                    Table table8 = new Table(columnWidth);
                    table8.addCell(setTextLeft(PROMO));

                    Table table9 = new Table(columnWidth2);
                    table9.addCell(setTextLeft(TOTAL_PRICE));
                    table9.addCell(setTextRight(String.valueOf(service.getTotal(sum, discount, DISCOUNT_PROMO))));

                    document.add(table7);
                    document.add(table8);
                    document.add(table9);

                } else {

                    Table table7 = new Table(columnWidth);
                    table7.addCell(setTextLeft(DISCOUNT + discount + "%"));

                    Table table8 = new Table(columnWidth);

                    Table table9 = new Table(columnWidth2);
                    table9.addCell(setTextLeft(TOTAL_PRICE));
                    table9.addCell(setTextRight(String.valueOf(service.getTotal(sum, discount, DISCOUNT_PROMO_NOT))));

                    document.add(table7);
                    document.add(table8);
                    document.add(table9);

                }

            } else {

                if (promoProducts > 5){

                    Table table7 = new Table(columnWidth);
                    table7.addCell(setTextLeft(CARD_EMPTY));

                    Table table8 = new Table(columnWidth);
                    table8.addCell(setTextLeft(PROMO));

                    Table table9 = new Table(columnWidth2);
                    table9.addCell(setTextLeft(TOTAL_PRICE));
                    table9.addCell(setTextRight(String.valueOf(service.getTotal(sum, discount, DISCOUNT_PROMO))));

                    document.add(table7);
                    document.add(table8);
                    document.add(table9);

                } else {

                    Table table7 = new Table(columnWidth);
                    table7.addCell(setTextLeft(CARD_EMPTY));

                    Table table8 = new Table(columnWidth);

                    Table table9 = new Table(columnWidth2);
                    table9.addCell(setTextLeft(TOTAL_PRICE));
                    table9.addCell(setTextRight(String.valueOf(service.getTotal(sum, discount, DISCOUNT_PROMO_NOT))));

                    document.add(table7);
                    document.add(table8);
                    document.add(table9);

                }
            }

        }
        outputStream.close();
    }

    private Cell setTextLeft(String text) {
        return new Cell().add(new Paragraph(text)).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT).setHeight(CELL_SIZE);
    }

    private Cell setTextCenter(String text) {
        return new Cell().add(new Paragraph(text)).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER).setHeight(CELL_SIZE);
    }

    private Cell setTextRight(String text) {
        return new Cell().add(new Paragraph(text)).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT).setHeight(CELL_SIZE);
    }
}
