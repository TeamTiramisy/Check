package com.console.check.servlet;

import com.console.check.dto.CardReadDto;
import com.console.check.service.CardService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cards")
public class CardsServlet extends HttpServlet {

    private final CardService cardService = CardService.getInstance();

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
