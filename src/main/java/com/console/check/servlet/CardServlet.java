package com.console.check.servlet;

import com.console.check.service.CardService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/card")
public class CardServlet extends HttpServlet {

    private final CardService cardService = CardService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));

        String card = cardService.findById(id)
                .map(cardReadDto -> new Gson().toJson(cardReadDto))
                .orElse("Invalid id");

        try(PrintWriter writer = resp.getWriter()){
            writer.write(card);
        }
    }
}
