package com.console.check.servlet;

import com.console.check.dto.ProductReadDto;
import com.console.check.service.CheckService;
import com.console.check.service.PrintCheckService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.console.check.util.Constants.PATH_CHECK_PDF;
import static com.console.check.util.Constants.PATH_CHECK_TXT;

@WebServlet("/check")
public class CheckServlet extends HttpServlet {

    private final CheckService checkService = CheckService.getInstance();
    private final PrintCheckService printCheckService = PrintCheckService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");

        List<ProductReadDto> products = checkService.findAllById(ids);
        printCheckService.print(products, PATH_CHECK_TXT, PATH_CHECK_PDF);

        try (OutputStream writer2 = resp.getOutputStream()){
            writer2.write(Files.readAllBytes(Paths.get(PATH_CHECK_PDF)));
        }

    }
}
