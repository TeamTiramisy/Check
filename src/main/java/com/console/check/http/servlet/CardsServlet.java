package com.console.check.http.servlet;

import com.console.check.dto.CardReadDto;
import com.console.check.service.CardService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CardsServlet extends HttpServlet {

    private final CardService cardService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String size = req.getParameter("size");
        String page = req.getParameter("page");

        List<CardReadDto> cards = cardService.findAll(size, page);
        String json = new Gson().toJson(cards);

        try(PrintWriter writer = resp.getWriter()){
            writer.write(json);
        }
    }
}
