package com.console.check.http.servlet;

import com.console.check.config.ApplicationConfiguration;
import com.console.check.dto.ProductReadDto;
import com.console.check.service.CheckService;
import com.console.check.service.PrintCheckService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.console.check.util.Constants.PATH_CHECK_PDF;


@Component
@RequiredArgsConstructor
public class CheckServlet extends HttpServlet {

    private final CheckService checkService;

    private final PrintCheckService printCheckService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");
        String[] qua = req.getParameterValues("qua");
        String card = req.getParameter("card");

        List<ProductReadDto> products = checkService.findAllById(ids, qua);
        printCheckService.printCheck(products, card);

        try (OutputStream writer2 = resp.getOutputStream()){
            writer2.write(Files.readAllBytes(Paths.get(PATH_CHECK_PDF)));
        }

    }
}
